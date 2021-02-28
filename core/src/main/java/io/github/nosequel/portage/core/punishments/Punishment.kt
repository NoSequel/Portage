package io.github.nosequel.portage.core.punishments

import io.github.nosequel.portage.core.PortageConstants
import io.github.nosequel.portage.core.expirable.Expirable
import java.util.UUID

class Punishment(
    val target: UUID,
    val type: PunishmentType,
    uuid: UUID,
    reason: String,
    executor: UUID = PortageConstants.consoleUuid,
    duration: Long = -1L,
    start: Long = System.currentTimeMillis(),
) : Expirable(uuid, reason, executor, duration, start)
