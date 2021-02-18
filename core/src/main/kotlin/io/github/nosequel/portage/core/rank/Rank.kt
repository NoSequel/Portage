package io.github.nosequel.portage.core.rank

import com.google.gson.annotations.SerializedName
import io.github.nosequel.portage.core.rank.metadata.Metadata
import java.util.UUID

class Rank(@SerializedName("_id") val uniqueId: UUID = UUID.randomUUID(), val name: String, val defaultMetadata: Metadata = Metadata.UNIDENTIFIED) {

    var prefix: String = ""
    var suffix: String = ""
    var color: String = "§f"

    var weight: Int = 0
    var hidden: Boolean = false

    var permissions: MutableSet<String> = mutableSetOf()
    var metadata: MutableSet<Metadata> = mutableSetOf()

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