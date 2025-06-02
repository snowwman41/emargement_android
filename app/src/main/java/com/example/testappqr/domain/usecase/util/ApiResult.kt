package com.example.testappqr.domain.usecase.util
import retrofit2.HttpException
import java.io.IOException

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception, val message: String? = null) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}



suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        val result = apiCall()
        ApiResult.Success(result)
    } catch (e: IOException) {
        ApiResult.Error(e, "Network error occurred")
    } catch (e: HttpException) {
        ApiResult.Error(e, "HTTP error: ${e.code()}")
    } catch (e: Exception) {
        ApiResult.Error(e, e.message ?: "Unknown error occurred")
    }
}

inline fun <T> ApiResult<T>.handle(
    onSuccess: (T) -> Unit = {},
    onError: (Exception, String) -> Unit = { _, _ -> },
    onLoading: () -> Unit = {}
) {
    when (this) {
        is ApiResult.Success -> onSuccess(data)
        is ApiResult.Error -> message?.let { onError(exception, it) }
        is ApiResult.Loading -> onLoading()
    }
}