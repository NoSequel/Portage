package io.github.nosequel.portage.bukkit.rank.prompt

import io.github.nosequel.portage.bukkit.rank.menu.editor.RankEditorMenu
import io.github.nosequel.portage.bukkit.util.chat.ChatPrompt
import io.github.nosequel.portage.bukkit.util.chat.ChatPromptResult
import io.github.nosequel.portage.core.rank.Rank
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class RankEditSuffixPrompt : ChatPrompt<Rank> {

    override fun getPromptText(player: Player, value: Rank): String {
        return "${ChatColor.YELLOW}Type a new suffix for the ${value.name} rank."
    }

    override fun handleInput(player: Player, message: String, value: Rank): ChatPromptResult {
        return ChatPromptResult("", true).also {
            value.suffix = message.replace("&", "§")
            RankEditorMenu(value, player).updateMenu()
        }
    }
}