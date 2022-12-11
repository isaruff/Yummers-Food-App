package com.example.graduationproject.data.datasource

import com.example.graduationproject.data.network.NetworkState
import retrofit2.Response
import java.io.IOException

open class ResponseResult {

    fun <T> getNetworkState(response: Response<T>?): NetworkState {
        return try {

            if (response?.isSuccessful == true) {
                if (response.body() != null) {
                    val data = response.body()
                    NetworkState.Success(data)
                } else {
                    NetworkState.InvalidData
                }

            } else {
                when (response?.code()) {
                    400 -> NetworkState.HttpErrors.BadRequest(response.message())
                    403 -> NetworkState.HttpErrors.ResourceForbidden(response.message())
                    404 -> NetworkState.HttpErrors.ResourceNotFound(response.message())
                    500 -> NetworkState.HttpErrors.InternalServerError(response.message())
                    502 -> NetworkState.HttpErrors.BadGateWay(response.message())
                    301 -> NetworkState.HttpErrors.ResourceRemoved(response.message())
                    302 -> NetworkState.HttpErrors.RemovedResourceFound(response.message())
                    else -> NetworkState.Error(response?.message())
                }

            }

        } catch (e: IOException) {
            NetworkState.NetworkException(e.message)
        }
    }

}