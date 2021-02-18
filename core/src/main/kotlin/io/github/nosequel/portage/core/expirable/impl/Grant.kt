package io.github.nosequel.portage.core.expirable.impl

import io.github.nosequel.portage.core.PortageConstants
import io.github.nosequel.portage.core.expirable.Expirable
import io.github.nosequel.portage.core.rank.Rank
import java.util.UUID

class Grant(val rank: Rank, reason: String = "Unidentified", executor: UUID = PortageConstants.consoleUuid, duration: Long=-1L, start: Long=System.currentTimeMillis()) : Expirable(reason, executor, duration, start)