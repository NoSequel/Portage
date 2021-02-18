package io.github.nosequel.portage.core.grant

import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.PortageConstants
import io.github.nosequel.portage.core.expirable.Expirable
import io.github.nosequel.portage.core.rank.Rank
import io.github.nosequel.portage.core.rank.RankHandler
import java.util.UUID

class Grant(
    val target: UUID,
    val rankId: String = PortageAPI.instance.handler.findOrThrow(RankHandler::class.java).findDefaultRank().name,
    uuid: UUID = UUID.randomUUID(),
    reason: String = "Unidentified",
    executor: UUID = PortageConstants.consoleUuid,
    duration: Long = -1L,
    start: Long = System.currentTimeMillis(),
) : Expirable(uuid, reason, executor, duration, start) {

    /**
     * Find the [Rank] object of the [Grant]
     *
     * @return the rank or null
     */
    fun findRank(): Rank {
        return PortageAPI.instance.handler.findOrThrow(RankHandler::class.java)
            .find(this.rankId).orElse(null)
    }
}