package com.example.graduationproject.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Food(
    val category: String,
    val id: Int,
    val image: String,
    val name: String,
    val price: Int
) : Parcelable