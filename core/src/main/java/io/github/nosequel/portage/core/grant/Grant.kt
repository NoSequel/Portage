package io.github.nosequel.portage.core.grant

import io.github.nosequel.portage.core.expirable.Expirable
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.Rank
import io.github.nosequel.portage.core.rank.RankHandler
import java.util.UUID

class Grant(
    val target: UUID,
    var rankId: String = HandlerManager.instance.findOrThrow(RankHandler::class.java).findDefaultRank().name,
    uuid: UUID = UUID.randomUUID(),
    reason: String = "Unidentified",
    executor: UUID = UUID.randomUUID(),
    duration: Long = -1L,
    start: Long = System.currentTimeMillis(),
) : Expirable(uuid, reason, duration, start, executor) {

    /**
     * Find the [Rank] object of the [Grant]
     *
     * @return the rank or null
     */
    fun findRank(): Rank {
        val rankHandler: RankHandler = HandlerManager.instance.findOrThrow(RankHandler::class.java)

        return rankHandler.find(this.rankId)
            .orElseGet {
                rankHandler.findDefaultRank()
                    .also { this.rankId = it.name }
            }
    }
}