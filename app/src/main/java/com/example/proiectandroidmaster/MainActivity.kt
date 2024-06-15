package com.example.proiectandroidmaster

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proiectandroidmaster.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import android.app.Application
import androidx.room.Room
import com.example.proiectandroidmaster.FoodDatabase

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


        FirebaseApp.initializeApp(this);
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main, LoginPage())
            .commit()
    }
}