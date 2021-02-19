package io.github.nosequel.portage.bukkit.rank.menu.editor.metadata.element

import io.github.nosequel.menus.button.ButtonBuilder
import io.github.nosequel.portage.core.rank.Rank
import io.github.nosequel.portage.core.rank.metadata.Metadata
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class RankMetadataToggleElement(index: Int, private val metadata: Metadata, val rank: Rank) : ButtonBuilder(index, Material.valueOf(metadata.displayItem)) {

    init {
        this.displayName(this.metadata.displayName)
        this.action { if (rank.hasMetadata(metadata)) rank.metadata.remove(metadata) else rank.metadata.add(metadata) }
        this.lore(
            "${ChatColor.GOLD}Enabled: ${if (rank.hasMetadata(metadata)) "${ChatColor.GREEN}Yes" else "${ChatColor.RED}No"}",
            "",
            "${ChatColor.GRAY}Click to toggle the metadata on/off",
        )
    }

    override fun toItemStack(): ItemStack {
        val itemStack: ItemStack = super.toItemStack()

        if (rank.hasMetadata(metadata)) {
            itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 10)
        }

        return itemStack
    }
}