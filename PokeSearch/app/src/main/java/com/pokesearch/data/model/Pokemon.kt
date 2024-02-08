package com.pokesearch.data.model

data class Pokemon(
    val id: Int,
    val name: String,
    val heightDecimeters: Float,
    val weightHectoGrams: Float,
    val types: List<PokemonType>,
    val imageUrl: String,
    val imageGifUrl: String,
)