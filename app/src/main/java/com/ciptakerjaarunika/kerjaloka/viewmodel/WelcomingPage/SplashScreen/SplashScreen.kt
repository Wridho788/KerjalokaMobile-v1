package com.ciptakerjaarunika.kerjaloka.viewmodel.WelcomingPage.SplashScreen

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.viewmodel.WelcomingPage.OnBoarding.OnBoarding

class SplashScreen : AppCompatActivity() {

    fun checkTheme() {
        val nightModeFlags: Int = application.resources
            .configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                Log.d("Night", "Night theme flags")
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                Log.d("Ligth", "Ligth theme flags")
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        checkTheme()

        val backgroundImg: LinearLayout = findViewById(R.id.onBoard)
        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)

        backgroundImg.startAnimation(sideAnimation)

        Handler().postDelayed({
            startActivity(Intent(this, OnBoarding::class.java))
            finish()
        }, 3000)
    }
}