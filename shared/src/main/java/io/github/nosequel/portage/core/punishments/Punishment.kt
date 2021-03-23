package io.github.nosequel.portage.core.punishments

import io.github.nosequel.portage.core.expirable.Expirable
import java.util.UUID

class Punishment(val target: UUID, val type: PunishmentType, uuid: UUID, reason: String, executor: UUID, duration: Long = -1L, start: Long = System.currentTimeMillis(), ) : Expirable(uuid, reason, duration, start, executor)
