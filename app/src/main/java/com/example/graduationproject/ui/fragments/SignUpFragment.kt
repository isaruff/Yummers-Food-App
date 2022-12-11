package com.example.graduationproject.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.graduationproject.R
import com.example.graduationproject.databinding.FragmentSignUpBinding
import com.example.graduationproject.ui.activities.LoginActivity
import com.example.graduationproject.utils.transitionDuration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerUser()


    }

    private fun registerUser(){
        database = FirebaseDatabase.getInstance().getReference("users")
        binding.buttonSignUp.setOnClickListener {
            val emailText = binding.emailEditText.text.toString().filter { !it.isWhitespace() }
            val passwordText = binding.passwordEditText.text.toString()

            when{
                emailText.isEmpty() ->{
                    binding.emailInputLayout.error = "Please enter the email"
                }
                passwordText.isEmpty() ->{
                    binding.emailInputLayout.error = "Please enter a proper password"
                }
                else ->{
                    auth = Firebase.auth
                    auth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener{ task ->
                            binding.loadingAnimation.isVisible = true
                            binding.loadingAnimation.playAnimation()
                            if (task.isSuccessful){
                                val handler = Handler(Looper.getMainLooper())
                                handler.postDelayed({
                                    val directions: NavDirections = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                                    findNavController().navigate(directions)
                                }, transitionDuration)

                            }else{
                                Toast.makeText(activity, "Registration Failed", Toast.LENGTH_LONG)
                                    .show()
                                binding.emailInputLayout.error = "Please enter valid information"
                                binding.loadingAnimation.pauseAnimation()
                                binding.loadingAnimation.isVisible = false
                            }
                        }
                }
            }


        }
    }


}