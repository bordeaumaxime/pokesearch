package com.pokesearch.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteOrderedPokemonType(
    // this is the actual order of the type in the list of types for a Pokemon
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: RemotePokemonType
)
