package io.github.nosequel.portage.core.database.mongo.repository

import java.util.Optional

interface MongoRepository<T> {

    /**
     * Retrieve all elements from a repository
     */
    fun retrieve(): MutableSet<T>

    /**
     * Retrieve an object from the repository
     */
    fun retrieve(id: String): Optional<T>

    /**
     * Update an object inside of the repository
     */
    fun update(value: T, id: String): Boolean

    /**
     * Delete an object from the repository
     */
    fun delete(value: T, id: String): Boolean

}