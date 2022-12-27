package com.example.graduationproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.graduationproject.data.model.FoodCart
import com.example.graduationproject.databinding.ItemFoodCartBinding
import com.google.android.material.snackbar.Snackbar

class CartAdapter(private val cancelClickListener: (Int) -> Unit) :
    ListAdapter<FoodCart, CartAdapter.CartViewHolder>(DiffCallback()) {

    class CartViewHolder(val binding: ItemFoodCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemFoodCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {

        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<FoodCart>() {
    override fun areItemsTheSame(oldItem: FoodCart, newItem: FoodCart): Boolean {
        return oldItem.cartId == newItem.cartId
    }

    override fun areContentsTheSame(oldItem: FoodCart, newItem: FoodCart): Boolean {
        return oldItem == newItem
    }
}
