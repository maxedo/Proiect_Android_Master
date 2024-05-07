package com.example.proiectandroidmaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proiectandroidmaster.databinding.FragmentLoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class LoginPage : Fragment() {

    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)
        val rootView = binding.root

        auth = FirebaseAuth.getInstance()

        binding.buttonLogin.setOnClickListener {
            val email = binding.usernameFieldLogin.text.toString().trim()
            val password = binding.passwordFieldLogin.text.toString().trim()
            loginUser(email, password)
        }

        binding.transferToRegisterText.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, RegisterPage())
                .addToBackStack(null)
                .commit()
        }

        return rootView
    }

    private fun loginUser(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login successful
                        Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                        // Optionally, navigate to a different screen
                    } else {
                        // Login failed
                        val message = task.exception?.message ?: "Unknown error"
                        Toast.makeText(requireContext(), "Login failed: $message", Toast.LENGTH_SHORT).show()
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