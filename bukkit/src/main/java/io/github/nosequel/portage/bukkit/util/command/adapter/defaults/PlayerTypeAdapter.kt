package io.github.nosequel.portage.bukkit.util.command.adapter.defaults

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

class PlayerTypeAdapter : TypeAdapter<Player> {

    @Throws(Exception::class)
    override fun convert(sender: CommandSender, source: String): Player {
        if (source.equals("@SELF", ignoreCase = true)) {
            return sender as Player
        }
        return if (Bukkit.getPlayer(source) == null) Bukkit.getPlayer(UUID.fromString(source))
        else Bukkit.getPlayer(source)
    }

    override val type: Class<Player>
        get() = Player::class.java
}