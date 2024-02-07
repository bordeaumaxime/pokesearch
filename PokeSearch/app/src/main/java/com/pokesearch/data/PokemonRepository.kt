package com.pokesearch.data

import com.pokesearch.data.commons.DataNetworkCaller.call
import com.pokesearch.data.commons.DataResult
import com.pokesearch.data.model.Pokemon
import com.pokesearch.data.remote.PokeApiService
import com.pokesearch.data.remote.model.toExternal

class PokemonRepository(private val pokeApiService: PokeApiService) {

    suspend fun getPokemon(idOrName: String): DataResult<Pokemon> {
        return call(transform = { it.toExternal() }) {
            pokeApiService.getPokemon(idOrName)
        }
    }
}