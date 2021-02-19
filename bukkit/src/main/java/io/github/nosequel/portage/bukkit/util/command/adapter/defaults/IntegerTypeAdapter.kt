package io.github.nosequel.portage.bukkit.util.command.adapter.defaults

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import org.bukkit.command.CommandSender


class IntegerTypeAdapter : TypeAdapter<Int> {
    
    @Throws(Exception::class)
    override fun convert(sender: CommandSender, source: String): Int {
        return source.toInt()
    }

    override val type: Class<Int>
        get() = Int::class.java
}