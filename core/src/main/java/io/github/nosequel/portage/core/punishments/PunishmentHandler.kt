package io.github.nosequel.portage.core.punishments

import com.google.gson.JsonObject
import io.github.nosequel.portage.core.expirable.ExpirationData
import io.github.nosequel.portage.core.grant.Grant
import io.github.nosequel.portage.core.handler.Handler
import io.github.nosequel.portage.core.punishments.redis.RedisPunishmentRepository
import io.github.nosequel.portage.core.punishments.redis.RedisPunishmentType
import java.util.Optional
import java.util.UUID
import java.util.stream.Collectors
import java.util.stream.Stream

class PunishmentHandler(val repository: PunishmentRepository, private val actionHandler: PunishmentActionHandler) : Handler {

    private val redis: RedisPunishmentRepository = RedisPunishmentRepository(repository.portageAPI, this)

    override fun enable() {
        this.repository.retrieveAsync().forEach { this.repository.cache.add(it) }
    }

    override fun disable() {
        this.stream().forEach { this.repository.updateAsync(it, it.uuid.toString()) }
    }

    /**
     * Open a new [Stream] for the cache of grants
     */
    fun stream(): Stream<Punishment> {
        return this.repository.cache.stream()
    }

    /**
     * Find an active [Punishment] by a [UUID] and a [PunishmentType]
     */
    fun findMostRelevantPunishment(uuid: UUID, type: PunishmentType): Optional<Punishment> {
        return this.findPunishmentByTarget(uuid, type).stream()
            .filter { it.isActive() }
            .findFirst()
    }

    /**
     * Find all the [Grant]s by a [UUID]
     */
    fun findPunishmentByTarget(uuid: UUID): Collection<Punishment> {
        return this.repository.cache.stream()
            .filter { it.target == uuid }
            .collect(Collectors.toList())
    }

    /**
     * Find all the [Punishment]s by a [UUID] and [PunishmentType]
     */
    fun findPunishmentByTarget(uuid: UUID, type: PunishmentType): Collection<Punishment> {
        return this.findPunishmentByTarget(uuid).stream()
            .filter { it.type == type }
            .collect(Collectors.toList())
    }

    /**
     * Expire a [Punishment]
     */
    fun expirePunishment(punishment: Punishment, reason: String): Punishment {
        return this.expirePunishment(punishment, ExpirationData(reason, System.currentTimeMillis()))
    }

    /**
     * Expire a [Punishment]
     */
    fun expirePunishment(punishment: Punishment, data: ExpirationData): Punishment {
        if (!this.repository.cache.contains(punishment)) {
            this.registerPunishment(punishment)
        } else if (!punishment.isActive()) {
            return punishment
        }

        punishment.expirationData = data
        punishment.expired = true

        this.repository.updateAsync(punishment, punishment.uuid.toString())
        this.actionHandler.expirePunishment(punishment)


        this.redis.publish(JsonObject().also { json ->
            kotlin.run {
                json.addProperty("type", RedisPunishmentType.ACTIVITY.name)
                json.addProperty("uuid", punishment.uuid.toString())
                json.addProperty("expired", true)
            }
        })

        return punishment
    }

    /**
     * Register a new [Punishment]
     */
    fun registerPunishment(punishment: Punishment): Punishment {
        if (this.stream().anyMatch { it.uuid == punishment.uuid }) {
            return punishment
        }

        this.findMostRelevantPunishment(punishment.target, punishment.type).ifPresent {
            if (it != punishment) {
                this.expirePunishment(it, "Overwrote by different punishment")
            }
        }

        this.repository.updateAsync(punishment, punishment.uuid.toString())
        this.repository.cache.add(punishment)

        this.redis.publishAsync(JsonObject().also { json ->
            kotlin.run {
                json.addProperty("type", RedisPunishmentType.ADDED.name)
                json.addProperty("uuid", punishment.uuid.toString())
            }
        })

        this.actionHandler.registerPunishment(punishment)

        return punishment
    }

    /**
     * Attempt to ban a player
     */
    fun attemptBan(target: UUID) {
        this.findMostRelevantPunishment(target, PunishmentType.BAN)
            .ifPresent { this.actionHandler.attemptBan(target, it) }
    }
}