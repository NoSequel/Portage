package io.github.nosequel.portage.bukkit.rank.adapter

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import io.github.nosequel.portage.core.handler.HandlerManager
import io.github.nosequel.portage.core.rank.Rank
import io.github.nosequel.portage.core.rank.RankHandler
import org.bukkit.command.CommandSender
import kotlin.jvm.Throws

class RankTypeAdapter : TypeAdapter<Rank> {

    private val rankHandler: RankHandler = HandlerManager.instance.findOrThrow(RankHandler::class.java)

    @Throws(Exception::class)
    override fun convert(sender: CommandSender, source: String): Rank {
        return this.rankHandler.find(source).orElse(null)
    }

    override val type: Class<Rank>
        get() = Rank::class.java
}