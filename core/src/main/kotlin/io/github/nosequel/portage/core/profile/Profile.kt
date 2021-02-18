package io.github.nosequel.portage.core.profile

import com.google.gson.annotations.SerializedName
import io.github.nosequel.portage.core.expirable.impl.Grant
import java.util.UUID

class Profile(@SerializedName("_id") val uuid: UUID, val name: String) {

    val grants: MutableSet<Grant> = mutableSetOf()

    /**
     * Find the most relevant [Grant] in the [Profile] object
     *
     * @return the most relevant [Grant]
     */
    fun findRelevantGrant(): Grant {
        return this.grants.stream()
            .filter { it.isActive() }.sorted(Comparator.comparingInt { grant: Grant -> grant.rank.weight })
            .findFirst().orElse(Grant().also { this.grants.add(it) })
    }
}