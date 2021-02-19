package io.github.nosequel.portage.bukkit.util.command.data

import io.github.nosequel.portage.bukkit.util.command.annotation.Subcommand
import java.lang.reflect.Method

class SubcommandData(val commandObject: Any, val method: Method) {

    val subcommand: Subcommand = method.getAnnotation(Subcommand::class.java)

}