package io.github.nosequel.portage.core.rank.metadata

enum class Metadata(val id: String, val displayName: String, val display: Boolean) {

    UNIDENTIFIED("Unidentified", "§cUnidentified", false),
    DEFAULT("Default", "§fDefault", true),
    DONATOR("Donator", "§aDonator", true),
    STAFF("Staff", "§9Staff", true),

}