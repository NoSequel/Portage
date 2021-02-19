package io.github.nosequel.portage.core.rank.metadata

enum class Metadata(val id: String, val displayName: String, val display: Boolean, val displayItem: String) {

    UNIDENTIFIED("Unidentified", "§cUnidentified", false, "AIR"),
    DEFAULT("Default", "§fDefault", true, "GRASS"),
    DONATOR("Donator", "§aDonator", true, "EMERALD"),
    STAFF("Staff", "§9Staff", true, "DIAMOND"),

}