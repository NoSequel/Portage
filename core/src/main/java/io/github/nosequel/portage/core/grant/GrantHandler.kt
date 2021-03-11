package io.github.nosequel.portage.core.grant

import io.github.nosequel.portage.core.handler.Handler
import java.util.UUID
import java.util.stream.Collectors
import java.util.stream.Stream

class GrantHandler(val repository: GrantRepository) : Handler {

    override fun enable() {
        this.repository.retrieveAsync().forEach { this.repository.cache.add(it) }
    }

    override fun disable() {
        this.stream().forEach { this.repository.updateAsync(it, it.uuid.toString()) }
    }

    /**
     * Open a new [Stream] for the cache of grants
     */
    fun stream(): Stream<Grant> {
        return this.repository.cache.stream()
    }

    /**
     * Find the most relevant [Grant] object for a [UUID]
     *
     * @return the grant
     */
    fun findMostRelevantGrant(uuid: UUID): Grant {
        return findGrantsByTarget(uuid).stream()
            .filter { it.isActive() }
            .findFirst()
            .orElseGet {
                Grant(uuid).also {
                    this.repository.cache.add(it); this.repository.updateAsync(it,
                    it.uuid.toString())
                }
            }
    }

    /**
     * Find all the [Grant]s by a [UUID]
     */
    fun findGrantsByTarget(uuid: UUID): Collection<Grant> {
        return this.repository.cache.stream()
            .filter { it.target == uuid }
            .sorted(Comparator.comparingInt { -it.findRank().weight })
            .collect(Collectors.toList())
    }


    /**
     * Register a [Grant] object to the cache
     *
     * @return the grant itself
     */
    fun registerGrant(grant: Grant): Grant? {
        if (this.stream().anyMatch { it.target == grant.target && it.rankId == grant.rankId && grant.duration == it.duration }) {
            return null
        }

        return grant.also { this.repository.cache.add(grant); this.repository.updateAsync(it, it.uuid.toString()) }
    }
}