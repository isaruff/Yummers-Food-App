package com.example.graduationproject.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.graduationproject.R
import com.example.graduationproject.databinding.FragmentFoodCartBinding
import com.example.graduationproject.ui.adapters.CartAdapter
import com.example.graduationproject.ui.viewmodels.FoodCartViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodCartFragment : Fragment() {
    private var _binding : FragmentFoodCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: FoodCartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    private val currentUser = FirebaseAuth.getInstance().currentUser?.email!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel.loadAllCarts(currentUser)
        setupRecyclerView()

    }


    private fun setupRecyclerView(){
        val cancelClickListener = CartAdapter.CancelClickListener{cartId ->
            cartViewModel.deleteCart(cartId, currentUser)
            cartViewModel.loadAllCarts(currentUser)
        }


        cartViewModel.foodCartList.observe(viewLifecycleOwner){
            cartAdapter.submitList(it)
        }

        cartAdapter = CartAdapter(cancelClickListener)
        binding.foodCartRecyclerView.adapter = cartAdapter
    }

}