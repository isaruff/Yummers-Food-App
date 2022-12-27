package com.example.graduationproject.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graduationproject.data.model.FoodList
import com.example.graduationproject.data.model.FoodWrapper
import com.example.graduationproject.data.network.NetworkState
import com.example.graduationproject.data.repository.ApiRepository
import com.example.graduationproject.utils.NETWORK_ERROR
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: ApiRepository) : ViewModel() {

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var myDatabaseRef: DatabaseReference
    private val currentUser = FirebaseAuth.getInstance().currentUser!!.uid

    private val _foodList = MutableLiveData<List<FoodWrapper>>()
    val foodList: LiveData<List<FoodWrapper>>
        get() = _foodList


    private val _favouritesList = MutableLiveData<MutableList<Int>>(mutableListOf())
    val favouritesList: LiveData<MutableList<Int>> get() = _favouritesList


    init {
        loadAllFavourites()
    }


    private fun loadFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getAllFoods()
            when (response) {
                is NetworkState.Success<*> -> {

                    Log.e("HERE", "${_favouritesList.value}")
                    _foodList.postValue((response.data as? FoodList)?.foods?.map {
                        FoodWrapper(it, _favouritesList.value?.any { int -> int == it.id } == true)
                    })
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

    fun setFavourite(foodId: Int) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        myDatabaseRef = firebaseDatabase.reference.child("users").child(currentUser).child("FoodID")
        _favouritesList.value =
            ArrayList(_favouritesList.value ?: emptyList()).apply { add(foodId) }
        Log.e("Set_Fav", "${_favouritesList.value}")
        myDatabaseRef.setValue(_favouritesList.value)
    }

    fun removeFavourite(foodId: Int) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        myDatabaseRef = firebaseDatabase.reference.child("users").child(currentUser).child("FoodID")
        _favouritesList.value = _favouritesList.value?.filter { it != foodId }?.toMutableList()
        Log.e("HERE REMOVE", _favouritesList.value.toString())
        myDatabaseRef.setValue(_favouritesList.value)
    }

    private fun loadAllFavourites() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        myDatabaseRef = firebaseDatabase.reference.child("users").child(currentUser).child("FoodID")
        myDatabaseRef.get().addOnSuccessListener {
            if (it.exists()) {
                _favouritesList.value =
                    (it.value as? MutableList<Int> ?: mutableListOf()).toSet()
                        .toMutableList() //Make items unique
                Log.e("Firebase", "${it.value}")
            } else {
                Log.e("Firebase", "Object does not exist")
            }
            if (_foodList.value.isNullOrEmpty()) loadFoods()
        }

    }

}