package io.github.nosequel.portage.core.database.mongo.repository.async

import io.github.nosequel.portage.core.database.mongo.repository.MongoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

interface AsyncMongoRepository<T> : MongoRepository<T> {

    /**
     * Retrieve an object from the repository using coroutines
     */
    fun retrieveAsync(): MutableSet<T> = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMongoRepository.retrieve()
    }

    fun retrieveAsync(id: String): T = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMongoRepository.retrieve(id)
    }

    /**
     * Update an object from the repository using coroutines
     */
    fun updateAsync(value: T): Boolean = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMongoRepository.update(value)
    }

    /**
     * Delete an object from the repository using coroutines
     */
    fun deleteAsync(value: T): Boolean = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMongoRepository.delete(value)
    }
}