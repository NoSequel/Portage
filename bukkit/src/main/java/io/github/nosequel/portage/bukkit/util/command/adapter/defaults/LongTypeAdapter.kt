package io.github.nosequel.portage.bukkit.util.command.adapter.defaults

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import org.bukkit.command.CommandSender
import kotlin.jvm.Throws

class LongTypeAdapter : TypeAdapter<Long> {

    @Throws(Exception::class)
    override fun convert(sender: CommandSender, source: String): Long {
        return source.toLong()
    }

    override val type: Class<Long>
        get() = Long::class.java
}