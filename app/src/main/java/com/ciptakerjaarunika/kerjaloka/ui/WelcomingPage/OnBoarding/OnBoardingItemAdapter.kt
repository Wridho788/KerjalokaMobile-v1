package com.ciptakerjaarunika.kerjaloka.ui.WelcomingPage.OnBoarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R


data class OnBoardingItem(
    val onboardingImage: Int,
    val title: String,
    val description: String
)

class OnBoardingItemAdapter(private val onboardingItems: List<OnBoardingItem>) :
RecyclerView.Adapter<OnBoardingItemAdapter.OnBoarding>()
{
    inner class OnBoarding(view: View) : RecyclerView.ViewHolder(view){
        private val imageOnBoarding = view.findViewById<ImageView>(R.id.imageOnBoarding)
        private val Title = view.findViewById<TextView>(R.id.Title)
        private val Description = view.findViewById<TextView>(R.id.Description)

        fun bind(onboardingItem: OnBoardingItem){
            imageOnBoarding.setImageResource(onboardingItem.onboardingImage)
            Title.text = onboardingItem.title
            Description.text = onboardingItem.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoarding {
        return OnBoarding(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_on_boarding2,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoarding, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }
}
