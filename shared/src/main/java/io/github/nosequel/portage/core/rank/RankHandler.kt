package io.github.nosequel.portage.core.rank

import io.github.nosequel.portage.core.handler.Handler
import io.github.nosequel.portage.core.rank.metadata.Metadata
import java.util.Optional
import java.util.stream.Stream
import kotlin.streams.toList

class RankHandler(val repository: RankRepository) : Handler {

    override fun enable() {
        this.repository.retrieveAsync().forEach {
            this.repository.cache.add(it)
        }
    }

    override fun disable() {
        this.getRanks().forEach {
            this.repository.updateAsync(it, it.name)
        }
    }

    /**
     * Get the [Stream] object of [MutableSet] of [Rank]s
     */
    fun getRanks(): List<Rank> {
        return this.repository.cache.sortedBy { -it.weight }
    }

    /**
     * Register a new [Rank] object
     */
    fun register(rank: Rank) {
        this.repository.cache.add(rank)
        this.repository.updateAsync(rank, rank.name)
    }

    /**
     * Delete an existing [Rank] object
     */
    fun delete(rank: Rank) {
        if (!this.repository.cache.remove(rank)) {
            println("Unable to remove ${rank.name} rank from the cache")
        }

        if (!this.repository.deleteAsync(rank, rank.name)) {
            println("Unable to delete ${rank.name} rank from the database}")
        }
    }

    /**
     * Find a [Rank] by a [String]
     *
     * @return the optional of the rank
     */
    fun find(name: String): Rank? {
        return this.repository.cache.firstOrNull { it.name.equals(name, true) }
    }

    /**
     * Find all ranks with a certain metadata
     */
    fun find(metadata: Metadata): List<Rank> {
        return this.getRanks()
                .filter { it.hasMetadata(metadata) }
                .toList()
    }

    /**
     * Find the default [Rank], automatically makes a [Rank] with DEFAULT [Metadata] if it does not exist
     *
     * @return the default rank
     */
    fun findDefaultRank(): Rank {
        return this.repository.cache.firstOrNull { it.hasMetadata(Metadata.DEFAULT) }
                ?: Rank("Default", Metadata.DEFAULT).also { this.register(it) }
    }
}