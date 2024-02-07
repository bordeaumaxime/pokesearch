package com.pokesearch.data.remote.model

import com.google.gson.annotations.SerializedName
import com.pokesearch.data.model.PokemonType

data class RemoteOrderedPokemonType(
    // this is the actual order of the type in the list of types for a Pokemon
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: RemotePokemonType
)

fun List<RemoteOrderedPokemonType>.toExternal(): List<PokemonType> =
    this.sortedBy { it.slot }
        .map { PokemonType(name = it.type.name) }
