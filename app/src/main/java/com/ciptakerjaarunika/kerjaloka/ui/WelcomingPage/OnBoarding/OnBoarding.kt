package com.ciptakerjaarunika.kerjaloka.ui.WelcomingPage.OnBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import me.relex.circleindicator.CircleIndicator3

class OnBoarding : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnBoardingItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding2)

        val btn_Skip = findViewById<TextView>(R.id.textSkip)

        btn_Skip.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        setOnBoardingItems()
    }



    private fun setOnBoardingItems(){

        onboardingItemsAdapter = OnBoardingItemAdapter(
            listOf(
                OnBoardingItem(
                    id =1,
                    onboardingImage = R.drawable.cv,
                    title = "Melamar Pekerjaan",
                    description = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
                    ),
                OnBoardingItem(
                    id=2,
                    onboardingImage = R.drawable.apply,
                    title = "Melamar Wanita Pujaanmu",
                    description = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
                ),
                OnBoardingItem(
                    id=3,
                    onboardingImage = R.drawable.cv,
                    title = "Lorem Ipsum",
                    description = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
                ),
                OnBoardingItem(
                    id =4,
                    onboardingImage = R.drawable.apply,
                    title = "Lorem Ipsum",
                    description = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet.",
                )
            )
        )
        val onboardLayer = findViewById<ViewPager2>(R.id.OnBoardingViewPager)
        onboardLayer.adapter = onboardingItemsAdapter
        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(onboardLayer)
    }
}
