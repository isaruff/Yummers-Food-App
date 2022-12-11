package com.example.graduationproject.utils

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

fun setSplashAnimation(view: View, toPercentage: Float, duration: Long,startAlpha: Float, endAlpha: Float) {
    val height = Resources.getSystem().displayMetrics.heightPixels
    val to = toPercentage * height / 100

    val translationY = ObjectAnimator.ofFloat(
        view,
        View.TRANSLATION_Y,
        to
    )

    val fadeAnimation = ObjectAnimator.ofFloat(
        view,
        View.ALPHA,
        startAlpha,
        endAlpha
    )


    translationY.duration = duration
    translationY.interpolator = AccelerateDecelerateInterpolator()
    translationY.start()

    fadeAnimation.duration = duration
    fadeAnimation.start()
}