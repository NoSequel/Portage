package io.github.nosequel.portage.core.rank

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.DatabaseHandler
import io.github.nosequel.portage.core.database.mongo.repository.async.AsyncMongoRepository
import org.bson.Document
import java.util.stream.Collectors

class RankRepository(private val portageAPI: PortageAPI) : AsyncMongoRepository<Rank> {

    private val collection: MongoCollection<Document> =
        portageAPI.handler.findOrThrow(DatabaseHandler::class.java).mongo.mongoDatabase!!
            .getCollection("ranks")

    override fun retrieve(): MutableSet<Rank> {
        return this.collection.find().toMutableList().stream()
            .map { this.portageAPI.gson.fromJson(it.toJson(), Rank::class.java) }
            .collect(Collectors.toSet())
    }

    override fun update(value: Rank): Boolean {
        return this.collection.updateOne(
            Filters.eq("_id", value.uniqueId),
            Document("\$set", Document.parse(this.portageAPI.gson.toJson(value))),
            UpdateOptions().upsert(true)
        ).wasAcknowledged()
    }

    override fun delete(value: Rank): Boolean {
        return this.collection.deleteOne(Filters.eq("_id", value.uniqueId)).wasAcknowledged()
    }

    override fun retrieve(id: String): Rank {
        return this.portageAPI.gson.fromJson(
            this.collection.find(Filters.eq("_id", id)).toMutableList().stream()
                .findFirst().orElseThrow { NullPointerException("Unable to find document with id $id") }
                .toJson(),
            Rank::class.java
        )
    }
}