package com.pokesearch.di

import com.pokesearch.data.PokemonRepository
import com.pokesearch.data.remote.PokeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providePokemonRepository(pokeApiService: PokeApiService): PokemonRepository {
        return PokemonRepository(pokeApiService)
    }
}