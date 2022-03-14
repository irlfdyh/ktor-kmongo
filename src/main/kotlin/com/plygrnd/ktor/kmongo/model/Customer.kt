package com.plygrnd.ktor.kmongo.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@kotlinx.serialization.Serializable
data class Customer(
    @Contextual
    @BsonId
    @SerialName("_id")
    val id: Id<Customer> = newId(),

    @SerialName("full_name")
    val fullName: String = ""
)
