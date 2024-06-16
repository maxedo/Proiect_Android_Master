package com.example.proiectandroidmaster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proiectandroidmaster.databinding.ItemFoodBinding
import android.util.Log
import com.example.proiectandroidmaster.Entities.Food

class FoodAdapter(private var foodList: List<Food>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.foodName.text = food.name
            binding.foodCalories.text = "Calories: ${food.calories} kcal"
            binding.foodProteins.text = "Proteins: ${food.protein} g"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun updateFoods(newFoodList: List<Food>) {
        Log.d("FoodAdapter", "Updating foods: ${newFoodList.size}")
        foodList = newFoodList
        notifyDataSetChanged()
    }
}
