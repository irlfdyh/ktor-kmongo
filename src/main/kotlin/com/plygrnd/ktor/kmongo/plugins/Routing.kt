package com.plygrnd.ktor.kmongo.plugins

import com.mongodb.client.MongoDatabase
import com.plygrnd.ktor.kmongo.route.customerRoute
import com.plygrnd.ktor.kmongo.route.productRoute
import com.plygrnd.ktor.kmongo.route.serializationRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting(database: MongoDatabase) {
    routing {
        route("/serialization") {
            serializationRoute(database)
        }
        customerRoute(database)
        productRoute(database)
    }
}
