package com.pokesearch.data.remote.model

import com.google.gson.annotations.SerializedName
import com.pokesearch.data.model.Pokemon
import com.pokesearch.data.model.PokemonType

data class RemotePokemon(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("height") val heightDecimeters: Float,
    @SerializedName("weight") val weightHectoGrams: Float,
    @SerializedName("types") val types: List<RemoteOrderedPokemonType>,
    @SerializedName("sprites") val sprites: RemotePokemonSprites,
)

fun RemotePokemon.toExternal() =
    Pokemon(
        id = id,
        name = name,
        heightDecimeters = heightDecimeters,
        weightHectoGrams = weightHectoGrams,
        types = types.toExternal(),
        imageUrl = sprites.frontDefaultUrl
    )
