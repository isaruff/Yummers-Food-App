package com.example.graduationproject.data.network

import com.example.graduationproject.utils.BASE_URL
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


 object ApiInitHelper{

        private lateinit var retrofit: Retrofit
        private val moshi = Moshi.Builder().build()

        private fun okHttpClient(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            okHttpClientBuilder.callTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)

            return okHttpClientBuilder.build()
        }


        private fun getClient(baseUrl: String): Retrofit {
            retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient())
                .baseUrl(baseUrl)
                .build()

            return retrofit
        }


     val foodApi : FoodApiService by lazy {
         getClient(BASE_URL).create(
             FoodApiService::class.java
         )
     }
    }



