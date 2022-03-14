package com.plygrnd.ktor.kmongo

import io.ktor.server.application.*
import com.plygrnd.ktor.kmongo.plugins.*
import org.litote.kmongo.KMongo

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val client = KMongo.createClient()
    val database = client.getDatabase("kmongo-ktor-test")
    configureSerialization()
    configureRouting(database)
}
