package io.github.nosequel.portage.core.grant

import io.github.nosequel.portage.core.expirable.ExpirationData
import io.github.nosequel.portage.core.grant.redis.RedisGrantRepository
import io.github.nosequel.portage.core.grant.redis.RedisGrantType
import io.github.nosequel.portage.core.handler.Handler
import java.util.UUID
import java.util.stream.Collectors
import java.util.stream.Stream

class GrantHandler(val repository: GrantRepository) : Handler {

    private val redis: RedisGrantRepository = RedisGrantRepository(repository.portageAPI, this)

    override fun enable() {
        this.repository.retrieveAsync().forEach {
            this.repository.cache.add(it)
        }
    }

    override fun disable() {
        this.stream().forEach {
            this.repository.updateAsync(it, it.uuid.toString())
        }
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
    fun findGrant(uuid: UUID): Grant {
        return findGrantsByTarget(uuid).stream()
            .filter { it.isActive() }
            .findFirst().orElseGet { this.registerGrant(Grant(uuid)) }
    }

    /**
     * Find all the [Grant]s by a [UUID]
     */
    fun findGrantsByTarget(uuid: UUID): Collection<Grant> {
        return this.repository.cache.stream()
            .filter { it.target == uuid }.sorted(Comparator.comparingInt { -it.findRank().weight })
            .collect(Collectors.toList())
    }


    /**
     * Register a [Grant] object to the cache
     *
     * @return the grant itself
     */
    fun registerGrant(grant: Grant): Grant {
        if (this.stream()
                .noneMatch { it.uuid == grant.uuid && it.target == grant.target && it.rankId == grant.rankId && grant.duration == it.duration }
        ) {
            this.repository.cache.add(grant);
            this.repository.updateAsync(grant, grant.uuid.toString())

            this.redis.publish(RedisGrantType.ADDED.toJson(grant))
        }

        return grant
    }

    /**
     * Expire an already existing [Grant] object
     */
    fun expireGrant(grant: Grant, data: ExpirationData): Grant {
        if (!this.repository.cache.contains(grant)) {
            this.registerGrant(grant)
        }

        grant.expirationData = data
        grant.expired = true

        this.repository.updateAsync(grant, grant.uuid.toString())
        this.redis.publish(RedisGrantType.ACTIVITY.toJson(grant))

        return grant
    }

    /**
     * Un-expire an already existing [Grant] object
     */
    fun unexpireGrant(grant: Grant): Grant {
        if (!this.repository.cache.contains(grant)) {
            this.registerGrant(grant)
        }

        grant.expirationData = null
        grant.expired = false

        this.repository.updateAsync(grant, grant.uuid.toString())
        this.redis.publishAsync(RedisGrantType.ACTIVITY.toJson(grant))

        return grant
    }

}