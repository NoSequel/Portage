package io.github.nosequel.portage.bukkit.util.command.adapter.defaults

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import org.bukkit.command.CommandSender
import java.util.UUID

class UUIDTypeAdapter : TypeAdapter<UUID> {

    @Throws(Exception::class)
    override fun convert(sender: CommandSender, source: String): UUID {
        return UUID.fromString(source)
    }

    override val type: Class<UUID>
        get() = UUID::class.java
}