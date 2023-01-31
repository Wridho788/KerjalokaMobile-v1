package com.ciptakerjaarunika.kerjaloka.viewmodel.WelcomingPage.OnBoarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import me.relex.circleindicator.CircleIndicator3
import java.lang.Boolean


class OnBoarding : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnBoardingItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding2)
//        val settings = getSharedPreferences("prefs", 0)
//        val editor = settings.edit()
//        editor.putBoolean("firstRun", true)
//        editor.commit()
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
        val btn_Skip = findViewById<TextView>(R.id.textSkip)
        btn_Skip.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setOnBoardingItems()
    }

    override fun onResume() {
        super.onResume()
        val settings = getSharedPreferences("prefs", 0)
        val firstRun = settings.getBoolean("firstRun", true)
        if (!firstRun) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.d("TAG1", "firstRun(false): " + Boolean.valueOf(firstRun).toString())
        } else {
            Log.d("TAG1", "firstRun(true): " + Boolean.valueOf(firstRun).toString())
        }
    }

    private fun setOnBoardingItems(){
        onboardingItemsAdapter = OnBoardingItemAdapter(
            listOf(
                OnBoardingItem(
                    id =1,
                    onboardingImage = R.drawable.temukan_beragam_pekerjaan,
                    title = "Temukan Beragam Pekerjaan",
                    description = "Temukan beragam pekerjaan yang kamu inginkan.",
                    ),
                OnBoardingItem(
                    id=2,
                    onboardingImage = R.drawable.beragam_test,
                    title = "Beragam Test",
                    description = "Temukan ribuan tes untuk pengembangan diri",
                ),
                OnBoardingItem(
                    id=3,
                    onboardingImage = R.drawable.cv,
                    title = "Buat e-CV",
                    description = "kirim e-cv hanya dengan menggunakan link",
                ),
                OnBoardingItem(
                    id =4,
                    onboardingImage = R.drawable.apply,
                    title = "Interview langsung",
                    description = "Atur tanggal dan langsung interview dengan perusahaan",
                )
            )
        )
        val onboardLayer = findViewById<ViewPager2>(R.id.OnBoardingViewPager)
        onboardLayer.adapter = onboardingItemsAdapter
        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(onboardLayer)
    }
}
