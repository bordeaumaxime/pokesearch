package com.pokesearch.data.commons

import retrofit2.Response
import java.io.IOException

object DataNetworkCaller {

    /**
     * Helper method for repositories to do reuse code that will handle errors
     * and return a [DataResult] instance.
     * This way repository users don't have to catch exceptions or interpret
     * http codes.
     */
    suspend fun <T: Any, R> call(
        transform: (R) -> T,
        networkCall: suspend () -> Response<R>
    ): DataResult<T> {
        val response = try {
            networkCall()
        } catch (e: IOException) {
            return DataResult.Error(DataResult.ErrorType.NETWORK)
        }
        val body = response.body()
        if (response.isSuccessful && body != null) {
            return DataResult.Success(transform(body))
        }
        return when (response.code()) {
            404 -> DataResult.Error(DataResult.ErrorType.NOT_FOUND)
            else -> DataResult.Error(DataResult.ErrorType.UNKNOWN)
        }
    }
}