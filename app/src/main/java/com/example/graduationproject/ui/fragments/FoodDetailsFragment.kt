package com.example.graduationproject.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import coil.load
import com.example.graduationproject.R
import com.example.graduationproject.data.model.FoodCart
import com.example.graduationproject.data.model.PostFoodCart
import com.example.graduationproject.databinding.FragmentFoodDetailsBinding
import com.example.graduationproject.ui.viewmodels.FoodDetailsViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailsFragment : Fragment() {
    private var _binding: FragmentFoodDetailsBinding? = null
    private val binding get() = _binding!!
    private var orderCount = 0
    private val args: FoodDetailsFragmentArgs by navArgs()
    private val detailsViewModel: FoodDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodDetailsBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setOrderCount()
        setupBackButton()
        addFoodIntoCart()
    }

    private fun setViews(){
        binding.foodImage.load("http://kasimadalan.pe.hu/foods/images/${args.foodData.image}")
        binding.priceTextView.text = args.foodData.price.toString()
        binding.foodNameText.text = args.foodData.name
        binding.orderCountTextView.text = orderCount.toString()
        binding.totalAmountTextView.text = "0"
    }

    private fun setOrderCount(){
        binding.buttonAdd.setOnClickListener{
            orderCount++
            binding.orderCountTextView.text = orderCount.toString()
            binding.totalAmountTextView.text = (args.foodData.price * orderCount).toString()
        }

        binding.buttonMinus.setOnClickListener {
            if (orderCount-1>=0){
                orderCount--
                binding.orderCountTextView.text = orderCount.toString()
                binding.totalAmountTextView.text = (args.foodData.price * orderCount).toString()
            }
        }
    }

    private fun setupBackButton(){
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun addFoodIntoCart(){

        binding.addToCart.setOnClickListener {
            val foodCartData = PostFoodCart(args.foodData.name,
                args.foodData.image,
                args.foodData.price,
                args.foodData.category,
                orderCount,
                FirebaseAuth.getInstance().currentUser?.email!!
            )
            detailsViewModel.insertIntoCart(foodCartData)
            Toast.makeText(activity, "Order is confirmed", Toast.LENGTH_SHORT).show()
        }

    }

}