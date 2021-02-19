package io.github.nosequel.portage.bukkit.util.chat

import io.github.nosequel.portage.core.handler.Handler
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.Optional

class ChatPromptHandler : Handler {

    val prompts: MutableSet<ChatPromptData<*>> = mutableSetOf()

    /**
     * Start a new [ChatPrompt] for a [Player]
     */
    fun <T> startPrompt(player: Player, prompt: ChatPrompt<T>, value: T) {
        if (this.findPrompt(player).isPresent) {
            player.sendMessage("${ChatColor.RED}You already have an active prompt running.")
            return
        }

        this.prompts.add(ChatPromptData(player, prompt, value))
            .also { player.sendMessage(prompt.getPromptText(player, value)) }
    }

    /**
     * Find a [ChatPrompt] by a [Player]
     */
    fun findPrompt(player: Player): Optional<ChatPromptData<*>> {
        return this.prompts.stream()
            .filter { it.target == player }
            .findAny()
    }
}