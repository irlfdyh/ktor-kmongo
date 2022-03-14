package com.plygrnd.ktor.kmongo.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json { serializersModule = IdKotlinXSerializationModule })
    }

    routing {
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
