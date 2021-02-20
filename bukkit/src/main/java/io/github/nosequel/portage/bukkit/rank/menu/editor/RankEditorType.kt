package io.github.nosequel.portage.bukkit.rank.menu.editor

import io.github.nosequel.menus.menu.Menu
import io.github.nosequel.portage.bukkit.rank.menu.editor.metadata.RankMetadataMenu
import io.github.nosequel.portage.bukkit.rank.prompt.RankEditColorPrompt
import io.github.nosequel.portage.bukkit.rank.prompt.RankEditPrefixPrompt
import io.github.nosequel.portage.bukkit.rank.prompt.RankEditSuffixPrompt
import io.github.nosequel.portage.bukkit.util.chat.ChatPromptHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.Rank
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import java.util.function.Consumer
import java.util.stream.Collectors

enum class RankEditorType(val displayName: String, val material: Material) {

    PREFIX("${ChatColor.GOLD}Edit Prefix", Material.NAME_TAG) {
        override fun getLore(rank: Rank): Array<String> {
            return arrayOf(
                "${ChatColor.GOLD}Current Value: ${ChatColor.RED}${rank.prefix}",
                "",
                "${ChatColor.GRAY}This will close the current menu to",
                "${ChatColor.GRAY}start a new prompt to change the prefix."
            )
        }

        override fun getAction(rank: Rank, clickType: ClickType, menu: Menu): Consumer<Player> {
            return Consumer { it.closeInventory(); this.promptHandler.startPrompt(it, RankEditPrefixPrompt(), rank) }
        }
    },

    SUFFIX("${ChatColor.GOLD}Edit Suffix", Material.NAME_TAG) {
        override fun getLore(rank: Rank): Array<String> {
            return arrayOf(
                "${ChatColor.GOLD}Current Value: ${ChatColor.RED}${rank.suffix}",
                "",
                "${ChatColor.GRAY}This will close the current menu to",
                "${ChatColor.GRAY}start a new prompt to change the suffix."
            )
        }

        override fun getAction(rank: Rank, clickType: ClickType, menu: Menu): Consumer<Player> {
            return Consumer { it.closeInventory(); this.promptHandler.startPrompt(it, RankEditSuffixPrompt(), rank) }
        }
    },

    COLOR("${ChatColor.GOLD}Edit Color", Material.INK_SACK) {
        override fun getLore(rank: Rank): Array<String> {
            return arrayOf(
                "${ChatColor.GOLD}Current Value: ${ChatColor.RED}${rank.color}",
                "",
                "${ChatColor.GRAY}This will close the current menu to",
                "${ChatColor.GRAY}start a new prompt to change the color."
            )
        }

        override fun getAction(rank: Rank, clickType: ClickType, menu: Menu): Consumer<Player> {
            return Consumer {
                it.closeInventory();
                this.promptHandler.startPrompt(it, RankEditColorPrompt(), rank)
            }
        }
    },

    WEIGHT("${ChatColor.GOLD}Edit Weight", Material.ANVIL) {
        override fun getLore(rank: Rank): Array<String> {
            return arrayOf(
                "${ChatColor.GOLD}Current Value: ${ChatColor.RED}${rank.weight}",
                "",
                "${ChatColor.GRAY}Left-click to increment the current weight.",
                "${ChatColor.GRAY}Right-click to decrease the current weigt."
            )
        }

        override fun getAction(rank: Rank, clickType: ClickType, menu: Menu): Consumer<Player> {
            return Consumer {
                rank.weight = if (clickType.isLeftClick) rank.weight + 1 else rank.weight - 1;
                menu.updateMenu()
            }
        }
    },

    METADATA("${ChatColor.GOLD}Edit Metadata", Material.COMMAND) {
        override fun getLore(rank: Rank): Array<String> {
            return arrayOf(
                "${ChatColor.GOLD}Current Value: ${ChatColor.RED}${
                    rank.metadata.stream().map { it.displayName }.collect(
                        Collectors.joining("${ChatColor.WHITE}, "))
                }"
            )
        }

        override fun getAction(rank: Rank, clickType: ClickType, menu: Menu): Consumer<Player> {
            return Consumer {
                it.closeInventory()
                RankMetadataMenu(it, rank).updateMenu()
            }
        }
    };

    val promptHandler: ChatPromptHandler = HandlerManager.instance.findOrThrow(ChatPromptHandler::class.java)

    abstract fun getLore(rank: Rank): Array<String>
    abstract fun getAction(rank: Rank, clickType: ClickType, menu: Menu): Consumer<Player>

}