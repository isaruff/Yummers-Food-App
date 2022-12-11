package com.example.graduationproject.data.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ApiResponse(
    val success: Int,
    val message: String
)
