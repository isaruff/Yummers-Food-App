package com.example.graduationproject.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject.data.model.PostFoodCart
import com.example.graduationproject.data.network.NetworkState
import com.example.graduationproject.data.repository.Repository
import com.example.graduationproject.utils.INSERT_SUCCESS
import com.example.graduationproject.utils.NETWORK_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(private val repo: Repository) : ViewModel(){


    fun insertIntoCart(foodCart: PostFoodCart){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.insertToCart(foodCart.name, foodCart.image, foodCart.price, foodCart.category, foodCart.orderAmount, foodCart.userName)
            when (response){
                is NetworkState.Success<*> ->{
                    Log.e(INSERT_SUCCESS, "$response")
                }
                is NetworkState.Error -> Log.e(NETWORK_ERROR, "$response.exceptionMessage")
                is NetworkState.HttpErrors -> Log.e(NETWORK_ERROR, "$response.exceptionMessage")
                is NetworkState.InvalidData -> {
                    Log.e(NETWORK_ERROR, "INVALID DATA")
                }
                is NetworkState.NetworkException -> Log.e(NETWORK_ERROR, "$response.exceptionMessage")
            }
        }
    }

}