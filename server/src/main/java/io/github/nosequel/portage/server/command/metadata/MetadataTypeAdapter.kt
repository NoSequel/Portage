package io.github.nosequel.portage.server.command.metadata

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import io.github.nosequel.portage.server.util.StaffMetadataUtil
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class MetadataTypeAdapter: TypeAdapter<StaffMetadataUtil.StaffMetadataType> {

    /**
     * Convert a String to the type
     *
     * @param sender the executor
     * @param source the string to get converted
     * @return the converted type
     * @throws Exception calls the handleException method
     */
    override fun convert(sender: CommandSender, source: String): StaffMetadataUtil.StaffMetadataType {
        return StaffMetadataUtil.StaffMetadataType.valueOf(source)
    }

    /**
     * Handle a thrown exception
     *
     * @param sender the executor
     * @param source the provided string
     */
    override fun handleException(sender: CommandSender, source: String) {
        sender.sendMessage("${ChatColor.RED}Available types: ${StaffMetadataUtil.StaffMetadataType.values().map { it.name }}")
    }

    override val type: Class<StaffMetadataUtil.StaffMetadataType>
        get() = StaffMetadataUtil.StaffMetadataType::class.java
}