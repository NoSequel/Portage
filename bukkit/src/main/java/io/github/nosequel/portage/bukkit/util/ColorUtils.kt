package io.github.nosequel.portage.bukkit.util

import io.github.nosequel.portage.core.rank.Rank
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import java.util.Optional

class ColorUtils {

    companion object {
        @JvmStatic
        val DYE_COLORS: Map<ChatColor, DyeColor> = mapOf(
            Pair(ChatColor.RED, DyeColor.RED),
            Pair(ChatColor.DARK_RED, DyeColor.RED),
            Pair(ChatColor.GREEN, DyeColor.GREEN),
            Pair(ChatColor.DARK_GREEN, DyeColor.GREEN),
            Pair(ChatColor.BLUE, DyeColor.BLUE),
            Pair(ChatColor.DARK_BLUE, DyeColor.BLUE),
            Pair(ChatColor.AQUA, DyeColor.BLUE),
            Pair(ChatColor.GOLD, DyeColor.ORANGE),
            Pair(ChatColor.YELLOW, DyeColor.YELLOW),
            Pair(ChatColor.GRAY, DyeColor.GRAY),
            Pair(ChatColor.DARK_GRAY, DyeColor.GRAY),
            Pair(ChatColor.BLACK, DyeColor.BLACK),
            Pair(ChatColor.WHITE, DyeColor.WHITE),
            Pair(ChatColor.LIGHT_PURPLE, DyeColor.MAGENTA),
            Pair(ChatColor.DARK_PURPLE, DyeColor.PURPLE)
        )

        @JvmStatic
        fun getRankColor(rank: Rank): ChatColor {
            val char = rank.color.replace("&", "").replace("§", "").toCharArray()[0]

            return ChatColor.values().asList().stream()
                .filter { it.char == char }
                .findFirst().orElseGet { ChatColor.WHITE }
        }
    }
}