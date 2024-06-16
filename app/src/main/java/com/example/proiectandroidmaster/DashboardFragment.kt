package com.example.proiectandroidmaster

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.proiectandroidmaster.databinding.FragmentDashboardBinding

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

        loadCalories()

        return binding.root
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
}
