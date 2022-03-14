package com.plygrnd.ktor.kmongo.route

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import com.plygrnd.ktor.kmongo.model.Jedi
import com.plygrnd.ktor.kmongo.model.StarWarsFilm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.*
import java.time.LocalDate
import java.time.Month
import kotlin.system.measureTimeMillis

fun Route.serializationRoute(database: MongoDatabase) {

    val collection = database.getCollection<Jedi>()

    get("/insert") {
        val parameters = this.call.request.queryParameters
        val name = parameters["name"] ?: "No Name"
        val age = parameters["age"]?.toInt() ?: 0
        val title = parameters["title"] ?: "No Title"
        collection.insertOne(Jedi(name, age, StarWarsFilm(title, LocalDate.of(1977, Month.MAY, 25))))
        call.respond(HttpStatusCode.Accepted, "parameter received with: name=$name : age=$age : title=$title")
    }

    get("/{name}") {
        val parameters = this.call.parameters
        val name = parameters["name"] ?: "No Name"
        val yoda = collection.findOne(Jedi::name eq name)
        call.respond(HttpStatusCode.Accepted, "parameter receive: name=$name, and data:$yoda")
    }

    get("/update") {
        val parameters = this.call.request.queryParameters
        val oldName = parameters["oldName"] ?: "No Name"
        val newName = parameters["newName"] ?: "No Name"
        val yoda = collection.updateOne(Jedi::name eq  oldName, setValue(Jedi::name, newName))
        call.respond(HttpStatusCode.Accepted, "parameter receive: name=$oldName and data:$yoda")
    }

    get("/delete/{name}") {
        val parameters = this.call.parameters
        val name = parameters["name"] ?: "No Name"
        var result: DeleteResult
        val time = measureTimeMillis {
            result = collection.deleteOne(Jedi::name eq name)
        }
        call.respond(HttpStatusCode.Accepted, "parameter receive: name=$name and result=$result withTime time=$time")
    }


}