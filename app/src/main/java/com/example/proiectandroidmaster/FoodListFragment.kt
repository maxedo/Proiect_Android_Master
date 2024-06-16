package com.example.proiectandroidmaster

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proiectandroidmaster.databinding.FragmentFoodListBinding
import com.example.proiectandroidmaster.localdb.FoodDatabase


class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodDatabase: FoodDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(inflater, container, false)

        foodDatabase = FoodDatabase.getInstance(requireContext())
        foodAdapter = FoodAdapter(listOf())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = foodAdapter
        }

        loadFoods()

        return binding.root
    }

    private fun loadFoods() {
        val sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("userEmail", "test@example.com") ?: "test@example.com"

        Log.d("FoodListFragment", "Fetching foods for email: $email")

        foodDatabase.foodDao().getFoodsByEmail(email).observe(viewLifecycleOwner) { foods ->
            if (foods != null && foods.isNotEmpty()) {
                Log.d("FoodListFragment", "Foods found: ${foods.size}")
                foodAdapter.updateFoods(foods)
            } else {
                Log.d("FoodListFragment", "No foods found")
            }
        }
    }
}
