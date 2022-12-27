package com.example.graduationproject.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject.data.entity.FoodCartEntity
import com.example.graduationproject.data.model.PostFoodCart
import com.example.graduationproject.data.network.NetworkState
import com.example.graduationproject.data.repository.ApiRepository
import com.example.graduationproject.data.repository.RoomRepository
import com.example.graduationproject.utils.INSERT_SUCCESS
import com.example.graduationproject.utils.NETWORK_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(private val roomRepository: RoomRepository) : ViewModel(){

    private var _orderAmount = MutableLiveData<Int?>()
    val orderAmount: LiveData<Int?> = _orderAmount

    fun insertIntoDatabase(foodCartEntity: FoodCartEntity){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addFoodCart(foodCartEntity)
        }
    }


}