package io.github.nosequel.portage.core.database.mongo.repository

interface MongoRepository<T> {

    /**
     * Retrieve all elements from a repository
     */
    fun retrieve(): MutableSet<T>

    /**
     * Retrieve an object from the repository
     */
    fun retrieve(id: String): T

    /**
     * Update an object inside of the repository
     */
    fun update(value: T): Boolean

    /**
     * Delete an object from the repository
     */
    fun delete(value: T): Boolean

}