package com.plygrnd.ktor.kmongo.model

import kotlinx.serialization.Contextual
import java.time.LocalDate

@kotlinx.serialization.Serializable
data class StarWarsFilm(
    val name: String,
    @Contextual val date: LocalDate
)
