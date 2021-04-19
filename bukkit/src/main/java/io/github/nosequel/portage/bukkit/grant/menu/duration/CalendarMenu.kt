package io.github.nosequel.portage.bukkit.grant.menu.duration

import io.github.nosequel.menus.MenuHandler
import io.github.nosequel.menus.button.Button
import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.menus.menu.PaginatedMenu
import io.github.nosequel.portage.bukkit.grant.prompt.GrantReasonPrompt
import io.github.nosequel.portage.bukkit.grant.prompt.data.GrantPromptData
import io.github.nosequel.portage.bukkit.util.chat.ChatPromptHandler
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.Rank
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

class CalendarMenu(player: Player, private val rank: Rank, private val target: UUID) : PaginatedMenu(player, "Select a Date", 9*4) {

    private var selectedDate = Date(System.currentTimeMillis())
    private val promptHandler: ChatPromptHandler = HandlerManager.findOrThrow(ChatPromptHandler::class.java)

    override fun getButtonsInRange(): MutableList<Button> {
        val buttons: MutableList<Button> = super.getButtonsInRange()

        buttons.add(
            ButtonBuilder(3, ItemStack(Material.WOOL, 1, DyeColor.GREEN.data.toShort()))
                .displayName("${ChatColor.GREEN}Confirm Date")
                .lore("${ChatColor.YELLOW}Current: ${ChatColor.LIGHT_PURPLE}${selectedDate}")
                .action {
                    player.closeInventory()
                    GrantPromptData(rank, target).also {
                        it.duration = selectedDate.time
                        this.promptHandler.startPrompt(player, GrantReasonPrompt(), it)
                    }
                    true
                }
        )

        buttons.add(
            ButtonBuilder(4, ItemStack(Material.WOOL, 1, DyeColor.YELLOW.data.toShort()))
                .displayName("${ChatColor.YELLOW}Permanent")
                .lore("${ChatColor.YELLOW}Set the duration of the grant to permanent")
                .action {
                    player.closeInventory()
                    GrantPromptData(rank, target).also {
                        it.duration = -1L
                        this.promptHandler.startPrompt(player, GrantReasonPrompt(), it)
                    }
                    true
                }
        )

        buttons.add(
            ButtonBuilder(5, ItemStack(Material.WOOL, 1, DyeColor.RED.data.toShort()))
                .displayName("${ChatColor.RED}Cancel Selection")
                .lore("${ChatColor.YELLOW}Current: ${ChatColor.LIGHT_PURPLE}${selectedDate}")
                .action {
                    player.closeInventory()
                    true
                }
        )

        return buttons
    }

    override fun getButtons(): MutableList<Button> {
        val buttons: MutableList<Button> = mutableListOf()
        val index = AtomicInteger(9)

        for (i in 0..8) {
            buttons.add(this.getEmptyButton().index(i))

        }

        this.getDates().stream()
            .map { date: Date ->
                ButtonBuilder(index.getAndIncrement(), Material.WATCH)
                    .displayName("${ChatColor.GOLD}${date}")
                    .lore("${ChatColor.GRAY}Click to change the current time")
                    .action {
                        this.selectedDate = date
                        true
                    }
            }.forEach { buttons.add(it) }

        return buttons
    }

    override fun onClose(event: InventoryCloseEvent) {
        MenuHandler.get().menus.remove(event.player)
    }

    /**
     * Get all [Date]s between the current date and in 5 years
     */
    private fun getDates(): Collection<Date> {
        val dates: MutableList<Date> = mutableListOf()
        val calendar = GregorianCalendar()

        val start = this.selectedDate
        val end = Date(this.selectedDate.time + 0x758FAC300)

        calendar.time = start

        while (calendar.time.before(end)) {
            dates.add(calendar.time)
            calendar.add(Calendar.DATE, 1)
        }

        return dates
    }

    /**
     * Get an empty [ButtonBuilder] object
     */
    private fun getEmptyButton(): ButtonBuilder {
        return ButtonBuilder(0, ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.data.toShort()))
            .displayName("")
            .action { true }
    }
}