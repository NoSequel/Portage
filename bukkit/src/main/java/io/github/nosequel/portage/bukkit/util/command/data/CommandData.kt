package io.github.nosequel.portage.bukkit.util.command.data

import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.bukkit.util.command.annotation.Subcommand
import java.lang.reflect.Method
import java.util.ArrayList

class CommandData(val commandObject: Any, val method: Method) {

    val command: Command = method.getAnnotation(Command::class.java)
    val subcommands: MutableList<SubcommandData> = mutableListOf()

    fun isParentOfSubCommand(subcommand: Subcommand): Boolean {
        return subcommand.parentLabel.equals(command.label, true)
    }
}