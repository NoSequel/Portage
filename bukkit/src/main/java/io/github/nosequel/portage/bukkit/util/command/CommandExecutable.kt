package io.github.nosequel.portage.bukkit.util.command

import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import io.github.nosequel.portage.bukkit.util.command.data.CommandData
import io.github.nosequel.portage.bukkit.util.command.data.SubcommandData
import org.apache.commons.lang.ArrayUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.util.Arrays
import java.util.Optional
import java.util.stream.Collectors

class CommandExecutable(private val data: CommandData) : Command(data.command.label) {

    init {
        if (data.command.permission.isNotEmpty()) {
            permission = data.command.permission
        }
        if (data.command.aliases.isNotEmpty()) {
            this.aliases = data.command.aliases.asList()
        }
    }

    override fun execute(sender: CommandSender, label: String, passedParameters: Array<String>): Boolean {
        val args: Array<String>
        val method: Method
        val permission: String
        val subcommand = this.findSubcommand(passedParameters)

        if (passedParameters.isNotEmpty() && data.subcommands.isNotEmpty() && subcommand.isPresent) {
            args = passedParameters.copyOfRange(1, passedParameters.size)
            method = subcommand.get().method
            permission = subcommand.get().subcommand.permission
        } else {
            args = passedParameters
            method = data.method
            permission = data.command.permission
        }

        if (permission.isNotEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage("${ChatColor.RED}xNo permission.")
            return false
        }

        if (method.parameters[0].type == Player::class.java && sender !is Player) {
            sender.sendMessage(ChatColor.RED.toString() + "You cannot execute that command as console.")
            return false
        }

        val parameters: Array<Parameter> = Arrays.copyOfRange(method.parameters, 1, method.parameters.size)

        if (parameters.isNotEmpty()) {
            val objects = arrayOfNulls<Any>(parameters.size)

            for (i in parameters.indices) {
                val parameter = parameters[i]
                val param =
                    parameter.getAnnotation(io.github.nosequel.portage.bukkit.util.command.annotation.Parameter::class.java)

                if (i >= args.size && (param == null || param.value.isEmpty() || param.value == "")) {
                    sender.sendMessage("${ChatColor.RED}Usage: /$label " + Arrays.stream(parameters)
                        .map { "<${it.type.simpleName.toLowerCase()}>" }.collect(Collectors.joining(" ")))
                    return true
                }

                val value: Any = if (param != null && param.value.isNotEmpty() && i >= args.size) {
                    param.value
                } else if (parameters[i].type.isArray) {
                    args.copyOfRange(i, args.size)
                } else {
                    args[i]
                }

                if (value is String) {
                    val typeAdapter: TypeAdapter<*>? = CommandHandler.instance.findConverter(parameter.type)

                    if (typeAdapter == null) {
                        objects[i] = value
                    } else {
                        try {
                            objects[i] = typeAdapter.convert(sender, value)
                            if (objects[i] == null) {
                                throw NullPointerException("Error while converting argument to object")
                            }
                        } catch (exception: Exception) {
                            typeAdapter.handleException(sender, value)
                            return true
                        }
                    }
                } else if (value is Array<*>) {
                    objects[i] = value
                }
            }

            method.invoke(data.commandObject, *ArrayUtils.add(objects, 0, sender))
        } else {
            method.invoke(data.commandObject, sender)
        }
        return false
    }

    /**
     * Find a [SubcommandData] object from a [Command]
     */
    private fun findSubcommand(parameters: Array<String>): Optional<SubcommandData> {
        if(parameters.isEmpty()) {
            return Optional.empty()
        }

        return data.subcommands.stream()
            .filter { subcommand ->
                subcommand.subcommand.label.equals(parameters[0], true)
                        || Arrays.stream(subcommand.subcommand.aliases)
                    .anyMatch { string -> string.equals(parameters[0], true) }
            }.findFirst()
    }
}