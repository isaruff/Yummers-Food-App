package com.example.graduationproject.ui.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.graduationproject.R
import com.example.graduationproject.data.model.Food
import com.example.graduationproject.data.model.FoodWrapper
import com.example.graduationproject.databinding.ItemFoodBinding

class FoodAdapter(
    private val onClickListener: OnClickListener,
    private val onRemoveFavorite: (Int) -> Unit,
    private val onSetFavorite: (Int) -> Unit,
) : ListAdapter<FoodWrapper, FoodAdapter.FoodViewHolder>(DiffCallback()) {

    class FoodViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val wrapper = getItem(position)
        val currentItem = wrapper.food
        Log.e("HERE BIND", "$wrapper")
        holder.binding.apply {
            foodImageView.load("http://kasimadalan.pe.hu/foods/images/${currentItem.image}")
            foodNameTextView.text = currentItem.name
            priceTextView.text = currentItem.price.toString()

            if (wrapper.isFavorite) {
                favoritesImageView.setImageResource(R.drawable.ic_heart_filled)
            } else {
                favoritesImageView.setImageResource(R.drawable.ic_heart_unselected)
            }

            root.setOnClickListener {
                onClickListener.onClick(currentItem)
            }
            favoritesImageView.setOnClickListener {
                if (wrapper.isFavorite) {
                    onRemoveFavorite(currentItem.id)
                    favoritesImageView.setImageResource(R.drawable.ic_heart_unselected)
                } else {
                    favoritesImageView.setImageResource(R.drawable.ic_heart_filled)
                    onSetFavorite(currentItem.id)
                }
                wrapper.isFavorite = wrapper.isFavorite.not()
                notifyItemChanged(position)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FoodWrapper>() {
        override fun areItemsTheSame(oldItem: FoodWrapper, newItem: FoodWrapper): Boolean {
            return oldItem.food.id == newItem.food.id
        }

        override fun areContentsTheSame(oldItem: FoodWrapper, newItem: FoodWrapper): Boolean {
            return oldItem == newItem
        }
    }


    class OnClickListener(val clickListener: (Food) -> Unit) {
        fun onClick(
            data: Food
        ) = clickListener(data)
    }

    class FavouritesClickListener(val clickListener: (Int) -> Unit) {
        fun onClick(
            id: Int
        ) = clickListener
    }
}