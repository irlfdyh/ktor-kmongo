package com.plygrnd.ktor.kmongo.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Tags(
    @SerialName("tags")
    val tags: List<String> = emptyList()
)
