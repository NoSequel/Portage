package io.github.nosequel.portage.bukkit.listener

import io.github.nosequel.portage.bukkit.util.chat.ChatPromptData
import io.github.nosequel.portage.bukkit.util.chat.ChatPromptHandler
import io.github.nosequel.portage.core.grant.Grant
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.Rank
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.Optional

class ChatPromptListener(private val handler: ChatPromptHandler) : Listener {

    private val grantHandler: GrantHandler = HandlerManager.instance.findOrThrow(GrantHandler::class.java)

    @EventHandler
    fun chat(event: AsyncPlayerChatEvent) {
        val player: Player = event.player
        val chatPrompt: Optional<ChatPromptData<*>> = this.handler.findPrompt(player)

        if (chatPrompt.isPresent) {
            chatPrompt.get().value?.let { any ->
                chatPrompt.get().prompt.handleInputCast(player, event.message, any)
                    .also {
                        event.isCancelled = it.cancelled; event.format = it.format; this.handler.prompts.remove(
                        chatPrompt.get())
                    }
            }
        } else {
            val grant: Grant = grantHandler.findMostRelevantGrant(player.uniqueId)
            val rank: Rank = grant.findRank()

            event.format = "${rank.prefix}${player.name}${rank.suffix}${ChatColor.GRAY}: ${ChatColor.WHITE}${
                event.message.replace("%", "$$")
            }"
        }
    }
}