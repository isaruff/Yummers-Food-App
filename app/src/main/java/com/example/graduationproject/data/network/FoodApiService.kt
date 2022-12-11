package com.example.graduationproject.data.network

import com.example.graduationproject.data.model.*
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodApiService {

    @GET("getAllFoods.php")
    suspend fun getAllFoods(): Response<FoodList>

    @POST("insertFood.php")
    @FormUrlEncoded
    suspend fun insertToCart(
        @Field("name") name: String,
        @Field("image") image: String,
        @Field("price") price: Int,
        @Field("category") category: String,
        @Field("orderAmount") orderAmount: Int,
        @Field("userName") userName: String
    ): Response<ApiResponse>

    @POST("getFoodsCart.php")
    @FormUrlEncoded
    suspend fun getFoodCart(
        @Field("userName") userName: String
    ): Response<FoodCartsList>

    @POST("deleteFood.php")
    @FormUrlEncoded
    suspend fun deleteFoodCart(
        @Field("cartId") cardId: Int,
        @Field("userName") userName: String
    ): Response<ApiResponse>
}