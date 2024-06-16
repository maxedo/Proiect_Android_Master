package com.example.proiectandroidmaster

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proiectandroidmaster.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import androidx.room.Room
import com.example.proiectandroidmaster.localdb.FoodDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var database: FoodDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            FoodDatabase::class.java,
            FoodDatabase.NAME
        ).build()

        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("userEmail", null)

        if (userEmail != null) {
            // Open DashboardActivity if user is logged in
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish() // Close MainActivity
        } else {
            // Open LoginPage fragment if user is not logged in
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, LoginPage())
                .commit()
        }
    }
}
