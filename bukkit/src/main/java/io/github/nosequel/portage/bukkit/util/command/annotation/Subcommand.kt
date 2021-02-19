package io.github.nosequel.portage.bukkit.util.command.annotation

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class Subcommand(

    val label: String,
    val parentLabel: String,
    val permission: String = "",
    val aliases: Array<String> = []

)