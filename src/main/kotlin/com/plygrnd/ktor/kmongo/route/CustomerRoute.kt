package com.plygrnd.ktor.kmongo.route

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import com.plygrnd.ktor.kmongo.model.Customer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId
import kotlin.system.measureTimeMillis

fun Route.customerRoute(database: MongoDatabase) {

    val collection = database.getCollection<Customer>()

    route("/customer") {

        get {
            val result = collection.find<Customer>()
            call.respond(HttpStatusCode.Accepted, result.toList())
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: "No ID"
            val result = collection.findOne(Customer::id eq ObjectId(id).toId())
            call.respond(HttpStatusCode.Accepted, result ?: "Not Found")
        }

        post {
            val request = call.receive<Customer>()
            var result: InsertOneResult
            val time = measureTimeMillis {
                result = collection.insertOne(request)
            }
            call.respond(HttpStatusCode.Accepted, "result: $result, time: $time")
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: "No ID"
            val request = call.receive<Customer>()
            var result: UpdateResult
            val time = measureTimeMillis {
                result = collection.updateOne(Customer::id eq ObjectId(id).toId(), setValue(Customer::fullName, request.fullName))
            }
            call.respond(HttpStatusCode.Accepted, "result: $result, time: $time")
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: "No ID"
            var result: DeleteResult
            val time = measureTimeMillis {
                result = collection.deleteOne(Customer::id eq ObjectId(id).toId())
            }
            call.respond(HttpStatusCode.Accepted, "result: $result, time: $time")
        }
    }
}