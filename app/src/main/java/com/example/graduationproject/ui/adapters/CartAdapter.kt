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

class CartAdapter(private val cancelClickListener: CancelClickListener): ListAdapter<FoodCart, CartAdapter.CartViewHolder>(DiffCallback()) {

    class CartViewHolder(val binding: ItemFoodCartBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(ItemFoodCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
                foodImageView.load("http://kasimadalan.pe.hu/foods/images/${currentItem.image}")
                foodNameTextView.text = currentItem.name
                totalAmountTextView.text = (currentItem.orderAmount * currentItem.price).toString()
                orderAmountText.text = currentItem.orderAmount.toString()
            buttonCancelOrder.setOnClickListener {
                Snackbar.make(it, "Do you want to cancel order?", Snackbar.LENGTH_SHORT).setAction("YES"){
                    cancelClickListener.onClick(currentItem.cartId)
                }.show()

            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FoodCart>(){
        override fun areItemsTheSame(oldItem: FoodCart, newItem: FoodCart): Boolean {
            return oldItem.cartId == newItem.cartId
        }

        override fun areContentsTheSame(oldItem: FoodCart, newItem: FoodCart): Boolean {
            return oldItem == newItem
        }
    }

    class CancelClickListener(val clickListener: (Int) -> Unit){
        fun onClick(
            cartId: Int
        ) = clickListener(cartId)
    }
}