package io.github.nosequel.portage.core.grant

import io.github.nosequel.portage.core.handler.Handler
import java.util.UUID

class GrantHandler(val repository: GrantRepository) : Handler {

    val cache: MutableSet<Grant> = mutableSetOf()

    override fun enable() {
        this.repository.retrieveAsync().forEach { this.cache.add(it) }
    }

    /**
     * Find the most relevant [Grant] object for a [UUID]
     *
     * @return the grant
     */
    fun findMostRelevantGrant(uuid: UUID): Grant {
        return this.cache.stream()
            .filter { it.target == uuid }
            .findFirst()
            .orElse(Grant(uuid).also { this.cache.add(it); this.repository.updateAsync(it, it.uuid.toString()) })
    }

    /**
     * Register a [Grant] object to the cache
     *
     * @return the grant itself
     */
    fun registerGrant(grant: Grant): Grant {
        return grant.also { this.cache.add(grant); this.repository.updateAsync(it, it.uuid.toString()) }
    }
}