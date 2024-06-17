package com.example.proiectandroidmaster

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.proiectandroidmaster.databinding.FragmentDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.buttonProgressBar.setOnClickListener {
            val intent = Intent(activity, AdaugaMancare::class.java)
            startActivity(intent)
        }

        binding.buttonTarget.setOnClickListener {
            showCustomDialog()
        }

        binding.buttonLogoff.setOnClickListener {
        logoff()
        }

        loadCalories()
        fetchCurrentValue() // Fetch and display current value from API

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        fetchCurrentValue() // Fetch and display current value when fragment is reopened
    }

    private fun loadCalories() {
        val sharedPreferences = requireActivity().getSharedPreferences("CaloriePrefs", Context.MODE_PRIVATE)
        val targetCalories = sharedPreferences.getInt("targetCalories", 0)
        binding.targetValue.text = "$targetCalories"
    }

    private fun saveTargetCalories(calories: Int) {
        val sharedPreferences = requireActivity().getSharedPreferences("CaloriePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("targetCalories", calories)
        editor.apply()

        binding.targetValue.text = "$calories"
    }

    private fun showCustomDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_custom)

        val editTextCalories = dialog.findViewById<EditText>(R.id.editTextCalories)
        val buttonDone = dialog.findViewById<Button>(R.id.buttonDone)

        buttonDone.setOnClickListener {
            val targetCalories = editTextCalories.text.toString().trim()
            if (targetCalories.isNotEmpty()) {
                saveTargetCalories(targetCalories.toInt())
                dialog.dismiss()
            } else {
                editTextCalories.error = "Please enter your target calories"
            }
        }

        dialog.show()
    }

    private fun fetchCurrentValue() {
        val sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("userEmail", "test@example.com") ?: "test@example.com"

        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                makeApiCall(email)
            }
            result?.let {
                binding.currentValue.text = it
            }
        }
    }

    private fun makeApiCall(email: String): String? {
        val url = URL("http://10.0.2.2:5000/FoodCurrentValue/$email") // Use 10.0.2.2 for Android emulator
        val connection = url.openConnection() as HttpURLConnection
        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                inputStream.bufferedReader().use { it.readText() }
            } else {
                Log.e("DashboardFragment", "API call failed with response code: $responseCode")
                null
            }
        } catch (e: Exception) {
            Log.e("DashboardFragment", "API call failed: ${e.message}", e)
            null
        } finally {
            connection.disconnect()
        }
    }
    private fun logoff() {
        // Clear shared preferences
        val sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Navigate back to MainActivity
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}

