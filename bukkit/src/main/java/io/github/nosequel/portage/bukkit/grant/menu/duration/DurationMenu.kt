package io.github.nosequel.portage.bukkit.grant.menu.duration

import io.github.nosequel.menus.MenuHandler
import io.github.nosequel.menus.button.Button
import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.menus.menu.Menu
import io.github.nosequel.portage.bukkit.grant.prompt.GrantReasonPrompt
import io.github.nosequel.portage.bukkit.grant.prompt.data.GrantPromptData
import io.github.nosequel.portage.bukkit.util.chat.ChatPromptHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.Rank
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryCloseEvent
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

class DurationMenu(player: Player, val target: UUID, val rank: Rank) : Menu(player, "Set Duration", 18) {

    private val promptData: GrantPromptData = GrantPromptData(rank, target)
    private val promptHandler: ChatPromptHandler = HandlerManager.instance.findOrThrow(ChatPromptHandler::class.java)

    override fun getButtons(): MutableList<Button> {
        val buttons = mutableListOf<Button>()
        val index = AtomicInteger(9)

        buttons.add(ButtonBuilder(0, Material.EMERALD)
            .displayName("${ChatColor.GREEN}Confirm Duration")
            .lore("${ChatColor.YELLOW}Current Duration: ${ChatColor.WHITE}${if (this.promptData.duration == -1L) "Permanent" else "${this.promptData.duration}"}")
            .action {
                player.closeInventory()
                this.promptHandler.startPrompt(player, GrantReasonPrompt(), promptData)
                true
            })

        DurationType.values().map {
            ButtonBuilder(index.getAndIncrement(), Material.WATCH)
                .displayName("${ChatColor.GOLD}${it.displayName}")
                .lore("${ChatColor.YELLOW}Click here to add this to the current duration")
                .action { type: ClickType -> // ugly hack, only made here so i can use `it` from the original lambda expression.
                    this.promptData.duration += it.duration
                    true
                }
        }.forEach { buttons.add(it) }

        return buttons
    }

    override fun onClose(event: InventoryCloseEvent) {
        MenuHandler.get().menus.remove(event.player)
    }
}