package io.github.nosequel.portage.core.punishments

import java.util.UUID

interface PunishmentActionHandler {

    /**
     * Attempt to ban the player
     */
    fun attemptBan(target: UUID, punishment: Punishment)

    /**
     * Handle the registration of a [Punishment]
     */
    fun registerPunishment(punishment: Punishment)

    /**
     * Handle the expiration of a [Punishment]
     */
    fun expirePunishment(punishment: Punishment)

}