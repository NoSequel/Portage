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
import java.util.Objects
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
        if (passedParameters.isNotEmpty() && data.subcommands.isNotEmpty() && data.subcommands.stream()
                .anyMatch { subcommand ->
                    subcommand.subcommand.label.equals(passedParameters[0],
                        true) || Arrays.stream(subcommand.subcommand.aliases)
                        .anyMatch { string -> string.equals(passedParameters[0], true) }
                }
        ) {
            val subcommand: SubcommandData = Objects.requireNonNull(data.subcommands.stream()
                .filter { subcommandData ->
                    subcommandData.subcommand.label.equals(passedParameters[0], true) || Arrays.stream(
                        subcommandData.subcommand.aliases).anyMatch { string ->
                        string.equals(passedParameters[0], true)
                    }
                }
                .findFirst().orElse(null))
            args = passedParameters.copyOfRange(1, passedParameters.size)
            method = subcommand.method
            permission = subcommand.subcommand.permission
        } else {
            args = passedParameters
            method = data.method
            permission = data.command.permission
        }

        if (permission.isNotEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage("${ChatColor.RED}No permission.")
            return false
        }

        if (method.parameters[0].type == Player::class.java && sender !is Player) {
            sender.sendMessage(ChatColor.RED.toString() + "You cannot execute that command as console.")
            return false
        }

        val parameters: Array<Parameter> = Arrays.copyOfRange(method.parameters, 1, method.parameters.size)
        if (parameters.isNotEmpty() && !parameters[0].type.isArray) {
            val objects = arrayOfNulls<Any>(parameters.size)

            for (i in parameters.indices) {
                val parameter = parameters[i]
                val param: io.github.nosequel.portage.bukkit.util.command.annotation.Parameter? =
                    parameter.getAnnotation(io.github.nosequel.portage.bukkit.util.command.annotation.Parameter::class.java)

                if (passedParameters.isEmpty() && (param == null || param.value.isEmpty() || param.value == "")) {
                    sender.sendMessage(ChatColor.RED.toString() + "Usage: /" + label + " " + Arrays.stream(
                        parameters)
                        .map { parameter1: Parameter -> "<" + parameter1.name + ">" }
                        .collect(Collectors.joining(" ")))
                    return true
                }

                val value: String =
                    if (param != null && i >= args.size && (param.value == "" || param.value.isEmpty())) {
                        sender.sendMessage(ChatColor.RED.toString() + "Usage: /" + label + " " + Arrays.stream(
                            parameters)
                            .map { parameter1: Parameter -> "<" + parameter1.name + ">" }
                            .collect(Collectors.joining(" ")))
                        return true
                    } else if (param != null && param.value.isNotEmpty() && i >= args.size) {
                        param.value
                    } else {
                        args[i]
                    }

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
            }

            method.invoke(data.commandObject, *ArrayUtils.add(objects, 0, sender))
        } else if (parameters.size == 1 && parameters[0].type.isArray) {
            method.invoke(data.commandObject, sender, args)
        } else {
            method.invoke(data.commandObject, sender)
        }
        return false
    }
}