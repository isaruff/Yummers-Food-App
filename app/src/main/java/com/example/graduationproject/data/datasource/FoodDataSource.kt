package com.example.graduationproject.data.datasource

import com.example.graduationproject.data.network.ApiInitHelper
import com.example.graduationproject.data.network.FoodApiService
import com.example.graduationproject.data.network.NetworkState
import java.io.IOException

class FoodDataSource: ResponseResult() {

    private val foodApi by lazy{
        ApiInitHelper.foodApi
    }

    suspend fun getAllFoods(): NetworkState {
        return try {
            val response = foodApi.getAllFoods()
            getNetworkState(response)
        } catch (e: Exception) {
            NetworkState.NetworkException(e.message)
        }
    }

    suspend fun insertToCart(
        name: String,
        image: String,
        price: Int,
        category: String,
        orderAmount: Int,
        userName: String
    ): NetworkState{
        return try {
            val response = foodApi.insertToCart(name, image, price, category, orderAmount, userName)
            getNetworkState(response)
        }catch (e:Exception){
            NetworkState.NetworkException(e.message)
        }
    }

    suspend fun getFoodCart(userName: String): NetworkState{
        return try {
            val response = foodApi.getFoodCart(userName)
            getNetworkState(response)
        }catch (e:Exception){
            NetworkState.NetworkException(e.message)
        }
    }

    suspend fun deleteFoodCart(userName: String, cartId: Int): NetworkState{
        return try {
            val response = foodApi.deleteFoodCart(cartId, userName)
            getNetworkState(response)
        }catch (e:Exception){
            NetworkState.NetworkException(e.message)
        }
    }
}