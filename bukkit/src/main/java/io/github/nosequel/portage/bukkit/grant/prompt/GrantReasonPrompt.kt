package io.github.nosequel.portage.bukkit.grant.prompt

import io.github.nosequel.portage.bukkit.grant.prompt.data.GrantPromptData
import io.github.nosequel.portage.bukkit.util.chat.ChatPrompt
import io.github.nosequel.portage.bukkit.util.chat.ChatPromptResult
import io.github.nosequel.portage.core.grant.Grant
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.UUID

class GrantReasonPrompt : ChatPrompt<GrantPromptData> {

    private val grantHandler: GrantHandler = HandlerManager.instance.findOrThrow(GrantHandler::class.java)

    override fun getPromptText(player: Player, value: GrantPromptData): String {
        return "${ChatColor.YELLOW}Please provide a reason for this grant."
    }

    override fun handleInput(player: Player, message: String, value: GrantPromptData): ChatPromptResult {
        return ChatPromptResult("", true).also {
            grantHandler.registerGrant(Grant(value.target,
                value.rank.name,
                UUID.randomUUID(),
                message,
                player.uniqueId,
                value.duration))

            Bukkit.getPlayer(value.target)
                ?.sendMessage("${ChatColor.LIGHT_PURPLE}${player.name}${ChatColor.YELLOW} has granted you the ${value.rank.getDisplayName()}${ChatColor.YELLOW} rank.")
            player.sendMessage("${ChatColor.YELLOW}You have granted the ${value.rank.getDisplayName()} ${ChatColor.YELLOW}rank.")
        }
    }
}