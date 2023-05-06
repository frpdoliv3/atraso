package com.fpoliveira.atraso.data.repository

import android.content.Context
import com.fpoliveira.atraso.R
import com.fpoliveira.atraso.domain.util.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository(
    private val context: Context
) {
    protected suspend fun <T>safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        return try {
            val response: Response<T> = apiCall()
            if (response.isSuccessful) {
                Resource.Success(data = response.body()!!)
            } else {
                Resource.Error(errorMessage = context.getString(R.string.api_generic_error))
            }
        } catch (e: HttpException) {
            Resource.Error(errorMessage = e.message ?: context.getString(R.string.api_generic_error))
        } catch (e: IOException) {
            Resource.Error(errorMessage = context.getString(R.string.api_internet_connection))
        } catch (e: Exception) {
            Resource.Error(context.getString(R.string.api_generic_error))
        }
    }
}
