package io.github.nosequel.portage.core.database.mongo.repository

import java.util.Optional

interface MapMongoRepository<K, V> {

    /**
     * Retrieve all elements from a repository
     */
    fun retrieve(): Map<K, V>

    /**
     * Find an object by the key
     */
    fun retrieve(key: K): Optional<V>

    /**
     * Update an object inside of the repository
     */
    fun update(value: V): Boolean

    /**
     * Delete an object from the repository
     */
    fun delete(value: V): Boolean
}