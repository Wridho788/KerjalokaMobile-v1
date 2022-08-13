package com.ciptakerjaarunika.kerjaloka.ui.WelcomingPage.OnBoarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.ciptakerjaarunika.kerjaloka.R

class OnBoarding : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnBoardingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding2)
        setOnBoardingItems()
    }

    private fun setOnBoardingItems(){
        onboardingItemsAdapter = OnBoardingItemAdapter(
            listOf(
                OnBoardingItem(
                    onboardingImage = R.drawable.apply,
                    title = "Melamar Pekerjaan",
                    description = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet."
                ),
                OnBoardingItem(
                    onboardingImage = R.drawable.cv,
                    title = "Melamar Wanita Pujaanmu",
                    description = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet."
                ),
                OnBoardingItem(
                    onboardingImage = R.drawable.apply,
                    title = "Lorem Ipsum",
                    description = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet."
                ),
                OnBoardingItem(
                    onboardingImage = R.drawable.cv,
                    title = "Lorem Ipsum",
                    description = "Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit. Exercitation veniam consequat sunt nostrud amet."
                )
            )
        )
        val onboardLayer = findViewById<ViewPager2>(R.id.OnBoardingViewPager)
        onboardLayer.adapter = onboardingItemsAdapter
    }
}
