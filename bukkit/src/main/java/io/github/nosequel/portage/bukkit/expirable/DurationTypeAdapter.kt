package io.github.nosequel.portage.bukkit.expirable

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import io.github.nosequel.portage.core.util.TimeUtil.parseToTime
import org.bukkit.command.CommandSender

class DurationTypeAdapter : TypeAdapter<Duration> {

    override fun convert(sender: CommandSender, source: String): Duration {
        return if (source.equals("perm", true) || source.equals("permanent", true)) {
            Duration(-1L)
        } else {
            Duration(source.parseToTime())
        }
    }

    override val type: Class<Duration>
        get() = Duration::class.java
}