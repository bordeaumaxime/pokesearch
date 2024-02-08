package com.pokesearch.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemotePokemonSprites(
    @SerializedName("front_default") val frontDefaultUrl: String,
    @SerializedName("other") val other: Other
) {
    data class Other(@SerializedName("showdown") val showdown: Showdown) {
        data class Showdown(@SerializedName("front_default") val frontDefaultUrl: String)
    }
}

