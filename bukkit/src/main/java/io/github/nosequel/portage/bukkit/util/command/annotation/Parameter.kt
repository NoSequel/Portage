package io.github.nosequel.portage.bukkit.util.command.annotation

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Parameter(val name: String, val value: String = "")