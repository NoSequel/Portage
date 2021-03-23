package io.github.nosequel.portage.core.rank.metadata

enum class Metadata(val displayName: String, val display: Boolean, val displayItem: String) {

    DEFAULT("§fDefault", true, "GRASS"),
    DONATOR("§aDonator", true, "EMERALD"),
    STAFF("§9Staff", true, "DIAMOND"),
    HIDDEN("§7Hidden", true, "GLASS")

}