package io.github.nosequel.portage.bukkit.grant.prompt.data

import io.github.nosequel.portage.core.rank.Rank
import java.util.UUID

class GrantPromptData(val rank: Rank, val target: UUID) {

    var duration: Long = -1L

}