package com.example.graduationproject.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject.data.model.*
import com.example.graduationproject.data.network.NetworkState
import com.example.graduationproject.data.repository.ApiRepository
import com.example.graduationproject.utils.NETWORK_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodCartViewModel @Inject constructor(private val repo: ApiRepository): ViewModel() {

    private val _foodCartList = MutableLiveData<List<FoodCart>>()
    val foodCartList : LiveData<List<FoodCart>> get() = _foodCartList



    fun loadAllCarts(userName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getFoodCart(userName)
            when (response) {
                is NetworkState.Success<*> -> {
                    Log.e("NETWORK_SUCCESS", "$response")
                    _foodCartList.postValue((response.data as? FoodCartsList)?.foods_cart)
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

    fun deleteCart(cartId: Int, userName: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.deleteFoodCart(userName, cartId)
            when (response){
                is NetworkState.Success<*> -> {
                   Log.e("NETWORK_SUCCESS", "$response")
                    loadAllCarts(userName)
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