package com.example.proiectandroidmaster

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proiectandroidmaster.databinding.ActivityDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomnavigation.setOnNavigationItemSelectedListener(bottomNavListener)

        // Load the default fragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DashboardFragment()).commit()
    }

    private val bottomNavListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment = DashboardFragment()
        when (item.itemId) {
            R.id.navigation_dashboard -> selectedFragment = DashboardFragment()
            R.id.navigation_food_list -> selectedFragment = FoodListFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
        true
    }
}
