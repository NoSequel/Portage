package io.github.nosequel.portage.core.database.mongo.repository

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.DatabaseHandler
import io.github.nosequel.portage.core.database.mongo.repository.async.AsyncMongoRepository
import org.bson.Document
import java.util.Optional
import java.util.stream.Collectors

open class DefaultMongoRepository<T>(private val portageAPI: PortageAPI, private val collection: String, private val clazz: Class<T>) : AsyncMongoRepository<T> {

    override fun retrieve(): MutableSet<T> {
        return this.getCollection().find().toMutableList().stream()
            .map { this.portageAPI.gson.fromJson(it.toJson(), clazz) }
            .collect(Collectors.toSet())
    }

    override fun update(value: T, id: String): Boolean {
        return this.getCollection().updateOne(
            Filters.eq("_id", id),
            Document("\$set", Document.parse(this.portageAPI.gson.toJson(value))),
            UpdateOptions().upsert(true)
        ).wasAcknowledged()
    }

    override fun delete(value: T, id: String): Boolean {
        return this.getCollection().deleteOne(Filters.eq("_id", id)).wasAcknowledged()
    }

    override fun retrieve(id: String): Optional<T> {
        val document: Document? = this.getCollection().find(Filters.eq("_id", id)).first();

        return if(document == null) {
            Optional.empty()
        } else {
            Optional.of(
                this.portageAPI.gson.fromJson(
                    document.toJson(),
                    this.clazz
                )
            )
        }
    }

    private fun getCollection(): MongoCollection<Document> {
        return portageAPI.handler.findOrThrow(DatabaseHandler::class.java)
            .mongo.mongoDatabase!!
            .getCollection(this.collection)
    }
}