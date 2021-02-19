package io.github.nosequel.portage.bukkit.rank.menu.editor.metadata

import io.github.nosequel.menus.MenuHandler
import io.github.nosequel.menus.button.Button
import io.github.nosequel.menus.menu.Menu
import io.github.nosequel.portage.bukkit.rank.menu.editor.metadata.element.RankMetadataToggleElement
import io.github.nosequel.portage.core.rank.Rank
import io.github.nosequel.portage.core.rank.metadata.Metadata
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import java.util.concurrent.atomic.AtomicInteger

class RankMetadataMenu(player: Player, val rank: Rank) : Menu(player, "Edit Rank Metadata", 18) {

    override fun getButtons(): MutableList<Button> {
        val index = AtomicInteger()

        return Metadata.values()
            .filter { it.display }
            .map { RankMetadataToggleElement(index.getAndIncrement(), it, rank) }
            .toCollection(mutableListOf())
    }

    override fun onClose(event: InventoryCloseEvent) {
        MenuHandler.get().menus.remove(event.player)
    }
}