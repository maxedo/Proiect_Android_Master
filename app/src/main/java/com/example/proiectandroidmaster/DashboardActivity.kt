package com.example.proiectandroidmaster

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proiectandroidmaster.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonProgressBar.setOnClickListener {
            val intent = Intent(this,AdaugaMancare ::class.java)
            startActivity(intent)
        }

        binding.buttonTarget.setOnClickListener{
            showCustomDialog()
        }


        loadCalories()

    }
    private fun loadCalories() {
        val sharedPreferences = getSharedPreferences("CaloriePrefs", Context.MODE_PRIVATE)
        val targetCalories = sharedPreferences.getInt("targetCalories", 0)

        binding.targetValue.text = "$targetCalories"
    }
    private fun saveTargetCalories(calories: Int) {
        val sharedPreferences = getSharedPreferences("CaloriePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("targetCalories", calories)
        editor.apply()

        binding.targetValue.text = "$calories"
    }


    private fun showCustomDialog() {
        val dialog = Dialog(this)
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

        // Show the dialog
        dialog.show()
    }

}