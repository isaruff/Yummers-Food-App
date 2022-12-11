package com.example.graduationproject.ui.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import com.example.graduationproject.R
import com.example.graduationproject.databinding.ActivitySplashBinding
import com.example.graduationproject.utils.setSplashAnimation
import com.example.graduationproject.utils.splashAnimDuration
import com.example.graduationproject.utils.transitionDuration

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSplashAnimation(binding.splashLogoView, -50F, splashAnimDuration, 0F, 1F)

        val handler = Handler(mainLooper)
        handler.postDelayed(
            {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            },
            transitionDuration
        )


    }
}