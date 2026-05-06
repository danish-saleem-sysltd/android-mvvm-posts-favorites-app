package com.example.mvvm.postsfavorites.core.network.handler

import com.example.mvvm.postsfavorites.core.common.di.IoDispatcher
import com.example.mvvm.postsfavorites.core.common.exception.ApiError
import com.example.mvvm.postsfavorites.core.common.exception.ApiException
import com.example.mvvm.postsfavorites.core.common.result.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertPathValidatorException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestHandler @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun <T> handle(block: suspend () -> T): Result<T> = withContext(ioDispatcher) {
        try {
            Result.Success(block())
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (api: ApiException) {
            Result.Error(api)
        } catch (http: HttpException) {
            Result.Error(http.toApiException())
        } catch (serialization: SerializationException) {
            Result.Error(
                ApiException(
                    apiError = ApiError.SerializationError,
                    message = serialization.message,
                ),
            )
        } catch (unknownHost: UnknownHostException) {
            Result.Error(
                ApiException(
                    apiError = ApiError.UnknownHost,
                    message = unknownHost.message,
                ),
            )
        } catch (timeout: SocketTimeoutException) {
            Result.Error(
                ApiException(
                    apiError = ApiError.Timeout,
                    message = timeout.message,
                ),
            )
        } catch (cert: CertPathValidatorException) {
            Result.Error(
                ApiException(
                    apiError = ApiError.Unknown,
                    message = cert.message,
                ),
            )
        } catch (throwable: Throwable) {
            Result.Error(
                ApiException(
                    apiError = ApiError.Unknown,
                    message = throwable.message,
                ),
            )
        }
    }

    private fun HttpException.toApiException(): ApiException {
        val code = code()
        val (message, errorCode) = parseErrorBody(response()?.errorBody()?.string())
        val apiError = when (code) {
            in 300..399 -> ApiError.RedirectError
            401 -> ApiError.Unauthorized
            422 -> ApiError.ValidationFailed
            in 400..499 -> ApiError.BadRequest
            in 500..599 -> ApiError.ServerError
            else -> ApiError.Unknown
        }
        return ApiException(
            apiError = apiError,
            message = message ?: this.message(),
            statusCode = code,
            errorCode = errorCode,
        )
    }

    private fun parseErrorBody(body: String?): Pair<String?, String?> {
        if (body.isNullOrBlank()) return null to null
        return try {
            val root = json.parseToJsonElement(body).jsonObject
            val message = root["message"]?.jsonPrimitive?.content
            val errorCode = (root["error"] as? JsonObject)
                ?.get("code")?.jsonPrimitive?.content
            message to errorCode
        } catch (_: Throwable) {
            null to null
        }
    }

    private companion object {
        val json = Json { ignoreUnknownKeys = true; isLenient = true }
    }
}
