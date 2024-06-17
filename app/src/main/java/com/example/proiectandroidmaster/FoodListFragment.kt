package com.example.proiectandroidmaster

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proiectandroidmaster.Entities.Food
import com.example.proiectandroidmaster.databinding.FragmentFoodListBinding
import com.example.proiectandroidmaster.localdb.FoodDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

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

        lifecycleScope.launch {
            try {
                val foods = withContext(Dispatchers.IO) {
                    foodDatabase.foodDao().getFoodsByEmail(email).value
                }

                if (foods != null && foods.isNotEmpty()) {
                    Log.d("FoodListFragment", "Foods found: ${foods.size}")
                    foodAdapter.updateFoods(foods)
                } else {
                    Log.d("FoodListFragment", "No foods found")
                }

                // Make the API call
                makeApiCall(email)
            } catch (e: Exception) {
                Log.e("FoodListFragment", "Error loading foods: ${e.message}", e)
            }
        }
    }

    private suspend fun makeApiCall(email: String) {
        withContext(Dispatchers.IO) {
            val url = URL("http://10.0.2.2:5000/FoodCurrent/$email") // Replace with your backend URL
            val connection = url.openConnection() as HttpURLConnection

            try {
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val result = inputStream.bufferedReader().use { it.readText() }
                    Log.d("FoodListFragment", "API call successful: $result")

                    // Parse the response and update the UI
                    val foods = parseFoods(result)
                    withContext(Dispatchers.Main) {
                        foodAdapter.updateFoods(foods)
                    }
                } else {
                    Log.e("FoodListFragment", "API call failed with response code: $responseCode")
                }
            } catch (e: Exception) {
                Log.e("FoodListFragment", "API call failed: ${e.message}", e)
            } finally {
                connection.disconnect()
            }
        }
    }

    private fun parseFoods(response: String): List<Food> {
        val jsonArray = JSONArray(response)
        val foods = mutableListOf<Food>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val food = Food(
                id = jsonObject.getInt("Id"),  // Corrected key name
                email = jsonObject.getString("Email"),  // Corrected key name
                foodCategory = jsonObject.getString("FoodCategory"),  // Corrected key name
                name = jsonObject.getString("Name"),  // Corrected key name
                calories = jsonObject.getInt("Calories"),  // Corrected key name
                protein = jsonObject.getInt("Protein")  // Corrected key name
            )
            foods.add(food)
        }
        return foods
    }
}
