package io.github.nosequel.portage.core.database.mongo.repository.async

import io.github.nosequel.portage.core.database.mongo.repository.MongoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.Optional

interface AsyncMongoRepository<T> : MongoRepository<T> {

    /**
     * Retrieve all objects from the repository using coroutines
     */
    fun retrieveAsync(): MutableSet<T> = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMongoRepository.retrieve()
    }

    /**
     * Retrieve an object from the repository using coroutines
     */
    fun retrieveAsync(id: String): Optional<T> = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMongoRepository.retrieve(id)
    }

    /**
     * Update an object from the repository using coroutines
     */
    fun updateAsync(value: T, id: String): Boolean = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMongoRepository.update(value, id)
    }

    /**
     * Delete an object from the repository using coroutines
     */
    fun deleteAsync(value: T, id: String): Boolean = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMongoRepository.delete(value, id)
    }
}