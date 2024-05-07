package com.example.proiectandroidmaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proiectandroidmaster.databinding.FragmentRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterPage : Fragment() {

    private var _binding: FragmentRegisterPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterPageBinding.inflate(inflater, container, false)
        val rootView = binding.root


        auth = FirebaseAuth.getInstance()


        binding.buttonRegister.setOnClickListener {
            val email = binding.emailFieldRegister.text.toString().trim()
            val password = binding.emailFieldRegister.text.toString().trim()
            registerUser(email, password)
        }

        return rootView
    }

    private fun registerUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show()
                        // Optionally, navigate to another page or clear the form
                    } else {
                        // Registration failed
                        val message = task.exception?.message ?: "Unknown error"
                        Toast.makeText(requireContext(), "Registration failed: $message", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(requireContext(), "Please enter both email and password.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}