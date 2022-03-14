package com.plygrnd.ktor.kmongo.model

import com.plygrnd.ktor.kmongo.util.Constants
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@kotlinx.serialization.Serializable
data class Category(
    @Contextual
    @BsonId
    @SerialName("_id")
    val id: Id<Category> = newId(),

    @SerialName("name")
    val name: String = Constants.INITIAL_STRING
)
