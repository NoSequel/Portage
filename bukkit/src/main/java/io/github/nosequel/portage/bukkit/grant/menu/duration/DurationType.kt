package io.github.nosequel.portage.bukkit.grant.menu.duration

enum class DurationType(val displayName: String, val duration: Long) {

    MINUTE("Minute", 0xEA60),
    HOUR("Hour", MINUTE.duration * 60),
    DAY("One Day", HOUR.duration * 24),
    WEEK("One Week", DAY.duration * 7),
    MONTH("One Month", WEEK.duration * 28),
    PERM("Permanent", -1L)

}