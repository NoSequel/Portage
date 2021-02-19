package io.github.nosequel.portage.bukkit.util.chat

import org.bukkit.entity.Player

class ChatPromptData<T>(val target: Player, val prompt: ChatPrompt<*>, val value: T)