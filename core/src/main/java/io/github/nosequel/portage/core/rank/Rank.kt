package io.github.nosequel.portage.core.rank

import com.google.gson.annotations.SerializedName
import io.github.nosequel.portage.core.rank.metadata.Metadata

class Rank(@SerializedName("_id") val name: String) {

    var prefix: String = ""
    var suffix: String = ""
    var color: String = "§f"

    var weight: Int = 0
    var hidden: Boolean = false

    var permissions: MutableSet<String> = mutableSetOf()
    var metadata: MutableSet<Metadata> = mutableSetOf()

    /**
     * Constructor to make a new [Rank] object with default [Metadata] values
     */
    constructor(name: String, vararg metadata: Metadata) : this(name) {
        metadata.forEach { this.metadata.add(it) }
    }

    /**
     * Check if a [Rank] object contains a certain [Metadata] value
     *
     * @return whether it contains the metadata
     */
    fun hasMetadata(metadata: Metadata): Boolean {
        return this.metadata.contains(metadata)
    }

    /**
     * Get the display name of a [Rank] object
     *
     * @return the display name
     */
    fun getDisplayName(): String {
        return "$color$name".replace("&", "§").replace("_", " ")
    }
}