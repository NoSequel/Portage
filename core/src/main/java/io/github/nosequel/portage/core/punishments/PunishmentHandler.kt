package io.github.nosequel.portage.core.punishments

import io.github.nosequel.portage.core.grant.Grant
import io.github.nosequel.portage.core.handler.Handler
import java.util.Optional
import java.util.UUID
import java.util.stream.Collectors
import java.util.stream.Stream

class PunishmentHandler(val repository: PunishmentRepository, private val actionHandler: PunishmentActionHandler) : Handler {

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
        return punishment.also {
            if (!this.repository.cache.contains(punishment)) {
                this.registerPunishment(punishment)
            }

            it.expire(reason)

            this.actionHandler.expirePunishment(it)
            this.repository.updateAsync(it, it.uuid.toString())
        }
    }

    /**
     * Register a new [Punishment]
     */
    fun registerPunishment(punishment: Punishment): Punishment {
        this.findMostRelevantPunishment(punishment.target, punishment.type).ifPresent {
            if (it != punishment) {
                it.expire("Overwrote by different punishment")
            }
        }

        return punishment.also {
            this.repository.updateAsync(it, it.uuid.toString())
            this.repository.cache.add(it)
            this.actionHandler.registerPunishment(it)
        }
    }

    /**
     * Attempt to ban a player
     */
    fun attemptBan(target: UUID) {
        this.findMostRelevantPunishment(target, PunishmentType.BAN)
            .ifPresent { this.actionHandler.attemptBan(target, it) }
    }
}