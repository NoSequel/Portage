package io.github.nosequel.portage.core.database.mongo

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoDatabase
import io.github.nosequel.portage.core.handler.Handler
import java.util.Collections

class MongoHandler (private val hostname: String, private val port: Int, private val database: String) : Handler {

    private var username: String? = null
    private var password: String? = null

    private var mongoClient: MongoClient? = null
    var mongoDatabase: MongoDatabase? = null

    /**
     * Constructor to make a new [MongoHandler] instance with authentication fields
     * Call this when you have a database with authentication enabled
     */
    constructor(hostname: String, port: Int, database: String, username: String, password: String) : this(
        hostname,
        port,
        database
    ) {
        this.username = username
        this.password = password
    }

    override fun enable() {
        this.mongoClient = if (this.password.isNullOrEmpty())
            MongoClient(this.hostname, this.port) else
            MongoClient(
                ServerAddress(this.hostname, this.port),
                Collections.singletonList(
                    MongoCredential.createCredential(
                        this.username!!,
                        this.database,
                        this.password!!.toCharArray()
                    )
                )
            )

        this.mongoDatabase = this.mongoClient!!.getDatabase(this.database)
    }
}