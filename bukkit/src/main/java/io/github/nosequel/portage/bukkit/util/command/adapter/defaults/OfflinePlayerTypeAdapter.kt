package io.github.nosequel.portage.bukkit.util.command.adapter.defaults

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

class OfflinePlayerTypeAdapter : TypeAdapter<OfflinePlayer> {

    @Throws(Exception::class)
    override fun convert(sender: CommandSender, source: String): OfflinePlayer {
        if (source.equals("@SELF", ignoreCase = true)) {
            return sender as Player
        }
        return if (Bukkit.getOfflinePlayer(source) == null) Bukkit.getOfflinePlayer(UUID.fromString(source))
        else Bukkit.getOfflinePlayer(source)
    }

    override val type: Class<OfflinePlayer>
        get() = OfflinePlayer::class.java
}