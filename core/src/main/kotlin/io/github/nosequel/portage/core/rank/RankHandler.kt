package io.github.nosequel.portage.core.rank

import io.github.nosequel.portage.core.handler.Handler
import io.github.nosequel.portage.core.rank.metadata.Metadata
import java.util.Optional
import java.util.UUID
import java.util.stream.Stream

class RankHandler(private val repository: RankRepository) : Handler {

    val cache: MutableSet<Rank> = mutableSetOf()

    /**
     * Get the [Stream] object of [MutableSet] of [Rank]s
     */
    fun stream(): Stream<Rank> {
        return this.cache.stream()
    }

    /**
     * Find a [Rank] by a [String]
     *
     * @return the optional of the rank
     */
    fun find(name: String): Optional<Rank> {
        return this.stream()
            .filter { it.name.equals(name, true) }
            .findAny()
    }

    /**
     * Find a [Rank] by a [UUID]
     *
     * @return the optional of the rank
     */
    fun find(uniqueId: UUID): Optional<Rank> {
        return this.stream()
            .filter { it.uniqueId == uniqueId }
            .findAny()
    }

    /**
     * Find the default [Rank], automatically makes a [Rank] with DEFAULT [Metadata] if it does not exist
     *
     * @return the default rank
     */
    fun findDefaultRank(): Rank {
        return this.stream()
            .filter { it.hasMetadata(Metadata.DEFAULT) }
            .findFirst().orElseGet {
                val rank = Rank(UUID.randomUUID(), "Default", Metadata.DEFAULT)
                this.repository.updateAsync(rank)

                rank
            }
    }
}