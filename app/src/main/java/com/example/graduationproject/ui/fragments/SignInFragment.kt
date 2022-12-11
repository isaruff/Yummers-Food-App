package com.example.graduationproject.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.graduationproject.databinding.FragmentSignInBinding
import com.example.graduationproject.ui.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var myDatabaseRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToSignUp()
        signInUser()
    }







    private fun signInUser() {
        binding.buttonSignIn.setOnClickListener {
            val emailText = binding.emailEditText.text.toString().filter { !it.isWhitespace() }
            val passwordText = binding.passwordEditText.text.toString()

            when {
                emailText.isEmpty() -> {
                    binding.emailInputLayout.error = "Please enter the email"
                }
                passwordText.isEmpty() -> {
                    binding.emailInputLayout.error = "Please enter a proper password"
                }
                else -> {
                    auth = Firebase.auth
                    auth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    activity,
                                    "The information is invalid",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                }
            }
        }
    }

    private fun navigateToSignUp() {
        val directions: NavDirections =
            SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        binding.createAccountTextView.setOnClickListener {
            Navigation.findNavController(it).navigate(directions)
        }
    }
}