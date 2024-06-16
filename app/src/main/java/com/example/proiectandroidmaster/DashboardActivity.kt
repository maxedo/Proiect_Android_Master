package com.example.proiectandroidmaster

import android.content.Context
import android.os.Bundle
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

        loadCalories()
//lowkey sunt putin braindead functiile de mai jos, idk exact cum sa facem; daca sa folosim butonu si sa facem popup,
//sau cand dam click pe text sa fie editabil si atat; ca placeholder pana vin de acasa e okeyish(ma duc la mag)
        binding.targetCaloriesText.setOnClickListener {
            //aici bagam implementare
            val newTargetCalories = 2000    //placeholder value, ne gandim cum schimbam, ar trebui sa mearga da n am cum sa testez trb sa intru n bios
            saveTargetCalories(newTargetCalories)
        }
//same as above
        binding.currentCaloriesText.setOnClickListener {
            // ---- // --------
            val newCurrentCalories = 500
            saveCurrentCalories(newCurrentCalories)
        }
    }
// n avea sens pt mn target sa fie in shared fara current, le am bagat pe aman2
    private fun loadCalories() {
        val sharedPreferences = getSharedPreferences("CaloriePrefs", Context.MODE_PRIVATE)
        val targetCalories = sharedPreferences.getInt("targetCalories", 0)
        val currentCalories = sharedPreferences.getInt("currentCalories", 0)

        binding.targetCaloriesText.text = "Target calories: $targetCalories"
        binding.currentCaloriesText.text = "Current calories: $currentCalories"
    }
//cand schimbam target calories sa l puna in shared, n am idee momentan cum, popup sau cv, vdm
    private fun saveTargetCalories(calories: Int) {
        val sharedPreferences = getSharedPreferences("CaloriePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("targetCalories", calories)
        editor.apply()

        binding.targetCaloriesText.text = "Target calories: $calories"
    }
//same ca mai sus
    private fun saveCurrentCalories(calories: Int) {
        val sharedPreferences = getSharedPreferences("CaloriePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("currentCalories", calories)
        editor.apply()

        binding.currentCaloriesText.text = "Current calories: $calories"
    }
}