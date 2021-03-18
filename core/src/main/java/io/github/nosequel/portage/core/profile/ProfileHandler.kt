package io.github.nosequel.portage.core.profile

import io.github.nosequel.portage.core.handler.Handler
import java.util.Optional
import java.util.UUID
import java.util.stream.Stream

class ProfileHandler(val repository: ProfileRepository) : Handler {

    override fun disable() {
        this.stream().forEach {
            this.repository.updateAsync(it, it.uuid.toString())
        }
    }

    /**
     * Get the [Stream] object of [MutableSet] of [Profile]s
     */
    fun stream(): Stream<Profile> {
        return this.repository.cache.stream()
    }

    /**
     * Find a [Profile] by a [String]
     *
     * @return the optional of the profile
     */
    fun find(name: String): Optional<Profile> {
        return this.stream()
            .filter { it.name.equals(name, true) }
            .findAny()
    }

    /**
     * Find a [Profile] by a [UUID]
     *
     * @return the optional of the profile
     */
    fun find(uuid: UUID): Optional<Profile> {
        return this.stream()
            .filter { it.uuid == uuid }
            .findAny()
    }

    /**
     * Save a [Profile] object to the [ProfileRepository]
     *
     * @return whether the action was successful or not
     */
    fun save(profile: Profile): Boolean {
        return this.repository.update(profile, profile.name)
    }

    /**
     * Load a [Profile] object from the [ProfileRepository]
     *
     * @return the profile
     */
    fun load(uuid: UUID, name: String): Profile {
        return this.find(uuid)
            .orElseGet {
                this.repository.retrieve(uuid.toString())
                    .orElseGet { Profile(uuid, name).also { this.repository.updateAsync(it, it.uuid.toString()) } }
            }.also { this.repository.cache.add(it) }
    }
}