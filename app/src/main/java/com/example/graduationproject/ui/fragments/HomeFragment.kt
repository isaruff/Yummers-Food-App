package com.example.graduationproject.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.graduationproject.R
import com.example.graduationproject.data.model.FoodWrapper
import com.example.graduationproject.databinding.FragmentHomeBinding
import com.example.graduationproject.ui.adapters.FoodAdapter
import com.example.graduationproject.ui.viewmodels.HomeViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var foodAdapter: FoodAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()


    }

    private fun setupRecyclerView() {
        val foodClickListener = FoodAdapter.OnClickListener { food ->
            val directions: NavDirections =
                HomeFragmentDirections.actionHomeFragmentToFoodDetailsFragment(food)
            findNavController().navigate(directions)
        }
        foodAdapter = FoodAdapter(
            onClickListener = foodClickListener,
            onRemoveFavorite = {
                homeViewModel.removeFavourite(it)
            },
            onSetFavorite = {
                homeViewModel.setFavourite(it)
            }
        )
        binding.foodRecyclerView.adapter = foodAdapter
        homeViewModel.foodList.observe(viewLifecycleOwner) {
            foodAdapter.submitList(it)
            search(it)
        }
    }

    private fun setupTabLayout() {
        val category = listOf("All", "Meals", "Desserts", "Drinks")
        category.forEach {
            binding.categoryTabLayout.addTab(
                binding.categoryTabLayout.newTab().setText(it)
            )
        }

        binding.categoryTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                refreshList(binding.categoryTabLayout.selectedTabPosition)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                refreshList(binding.categoryTabLayout.selectedTabPosition)
            }
        })

    }

    private fun refreshList(pos: Int) {
        homeViewModel.foodList.observe(viewLifecycleOwner) { foodList ->
            when (pos) {
                0 -> {
                    foodAdapter.submitList(foodList)
                    search(foodList)
                }
                1 -> {
                    val mealsList = foodList.filter { it.food.category == "Meals" }
                    foodAdapter.submitList(mealsList)
                    search(mealsList)
                }
                2 -> {
                    val dessertList = foodList.filter { it.food.category == "Desserts" }
                    foodAdapter.submitList(dessertList)
                    search(dessertList)
                }
                3 -> {
                    val drinksList = foodList.filter { it.food.category == "Drinks" }
                    foodAdapter.submitList(drinksList)
                    search(drinksList)
                }
            }
        }

    }

    private fun search(foodList: List<FoodWrapper>) {
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            foodAdapter.submitList(foodList.filter {
                it.food.name.lowercase().contains(text.toString().lowercase())
            })
        }
    }

}