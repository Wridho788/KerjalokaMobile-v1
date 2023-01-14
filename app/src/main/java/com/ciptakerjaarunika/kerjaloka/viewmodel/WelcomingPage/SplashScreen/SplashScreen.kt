package com.ciptakerjaarunika.kerjaloka.viewmodel.WelcomingPage.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.WelcomingPage.OnBoarding.OnBoarding

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val backgroundImg : LinearLayout = findViewById(R.id.onBoard)
        val sideAnimation = AnimationUtils.loadAnimation( this,R.anim.slide)

        backgroundImg.startAnimation(sideAnimation)

        Handler(). postDelayed({
          startActivity(Intent(this,OnBoarding::class.java))
            finish()
        },3000)
    }
}