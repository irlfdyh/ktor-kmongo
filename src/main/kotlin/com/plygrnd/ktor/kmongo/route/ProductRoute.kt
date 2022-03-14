package com.plygrnd.ktor.kmongo.route

import com.mongodb.client.MongoDatabase
import com.plygrnd.ktor.kmongo.model.Product
import com.plygrnd.ktor.kmongo.model.Tags
import com.plygrnd.ktor.kmongo.util.Constants
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId
import java.time.LocalDate

fun Route.productRoute(database: MongoDatabase) {

    val collection = database.getCollection<Product>()

    route("/product") {

        get {
            val products = collection.find()
            this.call.respond(products.toList())
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: "No ID"
            val product = collection.findOne(Product::id eq ObjectId(id).toId())
            this.call.respond(product ?: "Not Found")
        }

        post {
            val request = call.receive<Product>()
            val result = collection.insertOne(request)
            this.call.respond(result.toString())
        }

        // update identity data only
        put("/{id}") {
            val id = call.parameters["id"] ?: "No ID"
            val request = call.receive<Product>()
            val result = collection.updateOne(Product::id eq ObjectId(id).toId(), request.makeSetValue())
            this.call.respond(result.toString())
        }

        // update product tags
        put("/{id}/tags") {
            val id = call.parameters["id"] ?: "No ID"
            val request = call.receive<Tags>()
            val result = collection.updateOne(
                Product::id eq ObjectId(id).toId(),
                """
                    {
                        ${Constants.DOLLAR_SIGN}push: { tags: { ${Constants.DOLLAR_SIGN}each: ${request.tags.map { it.replace(it, "\"$it\"") }} } }
                    }
                """.trimIndent().formatJson()
            )
            this.call.respond(result.toString())
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: "No ID"
            val result = collection.deleteOne(Product::id eq ObjectId(id).toId())
            this.call.respond(result.toString())
        }

    }

}

fun Product.makeSetValue(): List<Bson> {
    val updatedProperties = mutableListOf<Bson>()
    if (this.categoryId != Constants.INITIAL_STRING) {
        updatedProperties.add(setValue(Product::categoryId, this.categoryId))
    }
    if (this.name != Constants.INITIAL_STRING) {
        updatedProperties.add(setValue(Product::name, this.name))
    }
    if (this.price != Constants.INITIAL_LONG) {
        updatedProperties.add(setValue(Product::price, this.price))
    }
    updatedProperties.add(setValue(Product::lastModifiedDate, LocalDate.now()))
    return updatedProperties
}
