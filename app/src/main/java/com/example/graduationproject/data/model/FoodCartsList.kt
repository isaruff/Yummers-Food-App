package com.example.graduationproject.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodCartsList(
    val foods_cart : List<FoodCart>
)
