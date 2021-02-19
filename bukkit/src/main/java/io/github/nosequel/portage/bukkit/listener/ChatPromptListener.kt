package io.github.nosequel.portage.bukkit.listener

import io.github.nosequel.portage.bukkit.util.chat.ChatPromptData
import io.github.nosequel.portage.bukkit.util.chat.ChatPromptHandler
import io.github.nosequel.portage.core.grant.GrantHandler
import io.github.nosequel.portage.core.handler.HandlerManager
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
            event.format = "${
                grantHandler.findMostRelevantGrant(player.uniqueId).findRank().prefix
            }%s${ChatColor.GRAY}: ${ChatColor.WHITE}%s"
        }
    }
}