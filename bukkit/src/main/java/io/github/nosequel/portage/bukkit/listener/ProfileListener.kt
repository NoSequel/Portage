package io.github.nosequel.portage.bukkit.listener

import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.profile.Profile
import io.github.nosequel.portage.core.profile.ProfileHandler
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.inventory.ItemStack
import java.lang.Exception

class ProfileListener : Listener {

    private val profileHandler: ProfileHandler = HandlerManager.instance.findOrThrow(ProfileHandler::class.java)

    @EventHandler(priority = EventPriority.HIGHEST)
    fun login(event: PlayerLoginEvent) {
        val player: Player = event.player

        val profile: Profile = try {
            profileHandler.load(player.uniqueId, player.name)
        } catch (exception: Exception) {
            player.kickPlayer(exception.stackTraceToString())
            throw exception
        }

        println("Successfully loaded profile with uniqueId ${profile.uuid}")
    }
}