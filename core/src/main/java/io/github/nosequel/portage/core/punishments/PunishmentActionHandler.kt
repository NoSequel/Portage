package io.github.nosequel.portage.core.punishments

import java.util.UUID

interface PunishmentActionHandler {

    /**
     * Attempt to ban the player
     */
    fun attemptBan(target: UUID, punishment: Punishment)

}