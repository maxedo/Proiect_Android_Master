package com.example.proiectandroidmaster

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proiectandroidmaster.DAO.NutritionResponse
import com.example.proiectandroidmaster.Entities.RetrofitInstance
import com.example.proiectandroidmaster.databinding.ActivityMancareBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AdaugaMancare : AppCompatActivity() {

    private lateinit var binding: ActivityMancareBinding
    private val apiKey = "MyfhyaKGR/pYq3QeECgrFQ==0YSOAFnq3RIrxBWC"
    private val TAG = "AdaugaMancare"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMancareBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.finalizareButton.setOnClickListener {
            val apiString = "${binding.gramajEditText.text}${binding.unitSpinner.selectedItem} ${binding.numeleMancariiEditText.text}"
            Log.d(TAG, "API String: $apiString")
            fetchNutritionInfo(apiString)
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.unitSpinner.adapter = adapter
        }
    }

    private fun fetchNutritionInfo(query: String) {
        Log.d(TAG, "Fetching nutrition info for query: $query")
        RetrofitInstance.api.getNutritionInfo(query, apiKey).enqueue(object :
            Callback<NutritionResponse> {
            override fun onResponse(call: Call<NutritionResponse>, response: Response<NutritionResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val nutritionItems = response.body()!!.items
                    if (nutritionItems.isNotEmpty()) {
                        val firstItem = nutritionItems[0]
                        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                        val email = sharedPreferences.getString("userEmail", null) ?: return
                        Log.d(TAG, "Fetched nutrition info: $firstItem")
                        postFoodToLocalDb(
                            email = email,
                            foodCategory = "exampleCategory",
                            name = firstItem.name,
                            calories = firstItem.calories,
                            proteins = firstItem.protein_g
                        )
                    } else {
                        Log.d(TAG, "No nutrition items found")
                    }
                } else {
                    Log.d(TAG, "Failed to fetch nutrition info: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<NutritionResponse>, t: Throwable) {
                Log.e(TAG, "Error fetching nutrition info", t)
            }
        })
    }

    private fun postFoodToLocalDb(email: String, foodCategory: String, name: String, calories: Double, proteins: Double) {
        Log.d(TAG, "Posting food to local DB: email=$email, foodCategory=$foodCategory, name=$name, calories=$calories, proteins=$proteins")
        val client = OkHttpClient()

        val json = JSONObject().apply {
            put("Email", email)
            put("FoodCategory", foodCategory)
            put("Name", name)
            put("Calories", calories)
            put("Proteins", proteins)
        }

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, json.toString())

        //10.0.2.2 pt emulator
        val request = Request.Builder()
            .url("http://10.0.2.2:5000/Food")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e(TAG, "Error posting food to local DB", e)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Successfully posted food to local DB")
                } else {
                    Log.d(TAG, "Failed to post food to local DB: ${response.message}")
                }
            }
        })
    }
}
