package io.github.nosequel.portage.core.rank

import io.github.nosequel.portage.core.handler.Handler
import io.github.nosequel.portage.core.rank.metadata.Metadata
import java.util.Optional
import java.util.stream.Stream

class RankHandler(val repository: RankRepository) : Handler {

    val cache: MutableSet<Rank> = mutableSetOf()

    override fun enable() {
        this.repository.retrieveAsync().forEach { this.cache.add(it) }
    }

    override fun disable() {
        this.stream().forEach { this.repository.updateAsync(it, it.name) }
    }

    /**
     * Get the [Stream] object of [MutableSet] of [Rank]s
     */
    fun stream(): Stream<Rank> {
        return this.cache.stream()
            .sorted(Comparator.comparingInt { -it.weight })
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
     * Find the default [Rank], automatically makes a [Rank] with DEFAULT [Metadata] if it does not exist
     *
     * @return the default rank
     */
    fun findDefaultRank(): Rank {
        return this.stream()
            .filter { it.hasMetadata(Metadata.DEFAULT) }
            .findFirst()
            .orElseGet {
                Rank("Default", Metadata.DEFAULT).also {
                    this.repository.updateAsync(it,
                        it.name); this.cache.add(it)
                }
            }
    }
}