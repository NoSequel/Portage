package io.github.nosequel.portage.bukkit.util.command.adapter

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

interface TypeAdapter<T> {
    /**
     * Convert a String to the type
     *
     * @param sender the executor
     * @param source the string to get converted
     * @return the converted type
     * @throws Exception calls the handleException method
     */
    @Throws(Exception::class)
    fun convert(sender: CommandSender, source: String): T

    /**
     * Tab complete the String to the type
     *
     * @param sender the executor
     * @param source the string
     * @return the returned tab complete string list
     */
    fun tabComplete(sender: CommandSender, source: String): List<String> {
        return emptyList()
    }

    /**
     * Handle a thrown exception
     *
     * @param sender the executor
     * @param source the provided string
     */
    fun handleException(sender: CommandSender, source: String) {
        sender.sendMessage("${ChatColor.RED}Error while parsing '$source' with that type.")
    }

    val type: Class<T>
}