package io.github.nosequel.portage.bukkit.util.command

import io.github.nosequel.portage.bukkit.expirable.DurationTypeAdapter
import io.github.nosequel.portage.bukkit.rank.adapter.RankTypeAdapter
import io.github.nosequel.portage.bukkit.util.command.adapter.TypeAdapter
import io.github.nosequel.portage.bukkit.util.command.adapter.defaults.*
import io.github.nosequel.portage.bukkit.util.command.annotation.Command
import io.github.nosequel.portage.bukkit.util.command.annotation.Subcommand
import io.github.nosequel.portage.bukkit.util.command.data.CommandData
import io.github.nosequel.portage.bukkit.util.command.data.SubcommandData
import io.github.nosequel.portage.core.handler.Handler
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.plugin.SimplePluginManager
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.Arrays

import java.util.stream.Collectors

class CommandHandler(val fallbackPrefix: String) : Handler {

    private val typeAdapters: MutableList<TypeAdapter<*>> = mutableListOf()
    private val commands: MutableList<CommandData> = ArrayList<CommandData>()

    /**
     * Register commands
     *
     * @param objects the commands
     */
    fun registerCommand(vararg objects: Any?) {
        objects.forEach {
            if (it != null) {
                this.register(it)
            }
        }
    }

    /**
     * Method to register a [Object] as a command
     *
     * @param object the object
     */
    fun register(`object`: Any) {
        val commandMethods = getMethods(Command::class.java, `object`)
        val subcommands = getMethods(Subcommand::class.java, `object`)

        commandMethods.stream()
            .map { method: Method -> CommandData(`object`, method) }
            .forEach { register(it) }

        subcommands.stream().filter { method: Method ->
            commands.stream()
                .anyMatch { data: CommandData -> data.isParentOfSubCommand(method.getAnnotation(Subcommand::class.java)) }
        }.forEach { method: Method ->
            commands.stream()
                .filter { data: CommandData -> data.isParentOfSubCommand(method.getAnnotation(Subcommand::class.java)) }
                .forEach { parent: CommandData -> parent.subcommands.add(SubcommandData(`object`, method)) }
        }
    }

    /**
     * Register a new [CommandData] object as a command
     *
     * @param data the command data object
     */
    private fun register(data: CommandData) {
        if (commandMap == null) {
            throw RuntimeException("commandMap field is null")
        }

        commands.add(data)
        commandMap!!.register(this.fallbackPrefix, CommandExecutable(data))
    }

    /**
     * Get all methods annotated with a [Annotation] in an object
     *
     * @param annotation the annotation which the method must be annotated with
     * @param object     the object with the methods
     * @param <T>        the type of the annontation
     * @return the list of methods
    </T> */
    private fun <T : Annotation?> getMethods(annotation: Class<T>, `object`: Any): List<Method> {
        return Arrays.stream(`object`.javaClass.methods)
            .filter { method: Method -> method.getAnnotation(annotation) != null }
            .collect(Collectors.toList())
    }

    /**
     * Find a converter by a class type
     *
     * @param type the type
     * @param <T>  the return type
     * @return the found type adapter
    </T> */
    fun <T> findConverter(type: Class<T>?): TypeAdapter<T>? {
        return typeAdapters.stream()
            .filter { converter: TypeAdapter<*> -> converter.type == type }
            .map { it as TypeAdapter<T> }
            .findAny().orElse(null)
    }

    /**
     * Gets the bukkit commandmap
     *
     * @return the commandmap
     */
    private val commandMap: CommandMap?
        get() {
            var commandMap: CommandMap? = null
            try {
                if (Bukkit.getPluginManager() is SimplePluginManager) {
                    val f: Field = SimplePluginManager::class.java.getDeclaredField("commandMap")
                    f.isAccessible = true
                    commandMap = f[Bukkit.getPluginManager()] as CommandMap
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return commandMap
        }

    companion object {
        lateinit var instance: CommandHandler
    }

    init {
        instance = this

        typeAdapters.addAll(listOf(
            UUIDTypeAdapter(),
            OfflinePlayerTypeAdapter(),
            LongTypeAdapter(),
            IntegerTypeAdapter(),
            PlayerTypeAdapter(),
            RankTypeAdapter(),
            DurationTypeAdapter(),
            BooleanTypeAdapter()
        ))
    }
}