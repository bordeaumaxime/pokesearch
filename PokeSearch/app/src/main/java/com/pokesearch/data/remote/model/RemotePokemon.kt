package com.pokesearch.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemotePokemon(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("height") val heightDecimeters: Float,
    @SerializedName("weight") val weightHectoGrams: Float,
    @SerializedName("types") val types: List<RemoteOrderedPokemonType>,
    @SerializedName("sprites") val sprites: RemotePokemonSprites,
)
