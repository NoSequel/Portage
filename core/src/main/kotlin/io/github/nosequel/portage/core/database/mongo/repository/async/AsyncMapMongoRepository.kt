package io.github.nosequel.portage.core.database.mongo.repository.async

import io.github.nosequel.portage.core.database.mongo.repository.MapMongoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.Optional

interface AsyncMapMongoRepository<K, V> : MapMongoRepository<K, V> {

    /**
     * Retrieve an object from the repository using coroutines
     */
    fun retrieveAsync(): Map<K, V> = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMapMongoRepository.retrieve()
    }

    /**
     * Find an object from the repository using coroutines
     */
    fun retrieveAsync(key: K): Optional<V> = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMapMongoRepository.retrieve(key)
    }

    /**
     * Update an object from the repository using coroutines
     */
    fun updateAsync(value: V): Boolean = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMapMongoRepository.update(value)
    }

    /**
     * Delete an object from the repository using coroutines
     */
    fun deleteAsync(value: V): Boolean = runBlocking(Dispatchers.IO) {
        return@runBlocking this@AsyncMapMongoRepository.delete(value)
    }
}