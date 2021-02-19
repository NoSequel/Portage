package io.github.nosequel.portage.bukkit.util.chat

import org.bukkit.entity.Player

interface ChatPrompt<T> {

    /**
     * Get the prompt text to send when the prompt start
     */
    fun getPromptText(player: Player, value: T): String

    /**
     * Handle the input of a [ChatPrompt] object
     */
    fun handleInput(player: Player, message: String, value: T): ChatPromptResult

    fun handleInputCast(player: Player, message: String, value: Any) : ChatPromptResult {
        return this.handleInput(player, message, value as T)
    }

}