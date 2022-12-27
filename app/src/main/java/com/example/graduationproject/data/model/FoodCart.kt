package com.example.graduationproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class FoodCart(
    val cartId: Int,
    val name: String,
    val image: String,
    val price: Int,
    val category: String,
    val orderAmount: Int,
    val userName: String
)