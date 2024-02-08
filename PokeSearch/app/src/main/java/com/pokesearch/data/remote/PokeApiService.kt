package com.pokesearch.data.remote

import com.pokesearch.data.remote.model.RemotePokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Performs request to the PokeAPI.
 * See documentation [here](https://pokeapi.co/docs/v2#pokemon)
 */
interface PokeApiService {

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    @GET("pokemon/{idOrName}")
    suspend fun getPokemon(@Path("idOrName") idOrName: String): Response<RemotePokemon>
}