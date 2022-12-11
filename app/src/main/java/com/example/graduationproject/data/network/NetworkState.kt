package com.example.graduationproject.data.network

sealed class NetworkState {
    data class Success<T>(val data: T) : NetworkState()
    data class Error(val errorMessage: String?) : NetworkState()
    data class NetworkException(val errorMessage: String?) : NetworkState()
    object InvalidData: NetworkState()

    sealed class HttpErrors(val exceptionMessage: String?) : NetworkState(){
        data class ResourceForbidden(val exception: String?) : HttpErrors(exception)
        data class ResourceNotFound(val exception: String?) : HttpErrors(exception)
        data class InternalServerError(val exception: String?) : HttpErrors(exception)
        data class BadGateWay(val exception: String?) : HttpErrors(exception)
        data class ResourceRemoved(val exception: String?) : HttpErrors(exception)
        data class RemovedResourceFound(val exception: String?) : HttpErrors(exception)
        data class BadRequest(val exception: String?) : HttpErrors(exception)
    }

}