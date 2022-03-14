package com.plygrnd.ktor.kmongo.model

import com.plygrnd.ktor.kmongo.util.Constants
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.LocalDate

@kotlinx.serialization.Serializable
data class Product(
    @Contextual
    @BsonId
    @SerialName("_id")
    val id: Id<Product> = newId(),

    @SerialName("category_id")
    val categoryId: String = Constants.INITIAL_STRING,

    @SerialName("name")
    val name: String = Constants.INITIAL_STRING,

    @SerialName("price")
    val price: Long = Constants.INITIAL_LONG,

    @SerialName("tags")
    val tags: List<String> = emptyList(),

    @SerialName("stock")
    val stock: Int = Constants.INITIAL_INTEGER,

    @Contextual
    @SerialName("last_modified_date")
    val lastModifiedDate: LocalDate = LocalDate.now(),

    @SerialName("ratings")
    val ratings: List<Int> = emptyList()
)