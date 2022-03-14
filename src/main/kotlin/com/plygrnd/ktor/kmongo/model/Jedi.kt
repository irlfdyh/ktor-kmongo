package com.plygrnd.ktor.kmongo.model

@kotlinx.serialization.Serializable
data class Jedi(val name: String, val age: Int, val firstAppearance: StarWarsFilm)