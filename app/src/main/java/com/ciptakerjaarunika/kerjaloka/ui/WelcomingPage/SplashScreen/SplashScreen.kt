package com.ciptakerjaarunika.kerjaloka.ui.WelcomingPage.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val backgroundImg : LinearLayout = findViewById(R.id.onBoard)
        val sideAnimation = AnimationUtils.loadAnimation( this,R.anim.slide)

        backgroundImg.startAnimation(sideAnimation)

        Handler(). postDelayed({
          startActivity(Intent(this,MainActivity::class.java))
            finish()
        },3000)
    }
}