package com.example.graduationproject.data.repository

import com.example.graduationproject.data.datasource.FoodDataSource
import com.example.graduationproject.data.network.NetworkState

class Repository(private val fds: FoodDataSource) {

    suspend fun getAllFoods(): NetworkState = fds.getAllFoods()

    suspend fun insertToCart(
        name: String,
        image: String,
        price: Int,
        category: String,
        orderAmount: Int,
        userName: String
    ): NetworkState = fds.insertToCart(name, image, price, category, orderAmount, userName)

    suspend fun getFoodCart(userName: String): NetworkState = fds.getFoodCart(userName)

    suspend fun deleteFoodCart(userName: String, cartId: Int): NetworkState = fds.deleteFoodCart(userName, cartId)
}