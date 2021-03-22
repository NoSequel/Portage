package io.github.nosequel.portage.bukkit.util.command.adapter.defaults

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import org.bukkit.command.CommandSender

class BooleanTypeAdapter: TypeAdapter<Boolean> {

    /**
     * Convert a String to the type
     *
     * @param sender the executor
     * @param source the string to get converted
     * @return the converted type
     * @throws Exception calls the handleException method
     */
    override fun convert(sender: CommandSender, source: String): Boolean {
        return source.toBoolean()
    }

    override val type: Class<Boolean>
        get() = Boolean::class.java
}