package com.ciptakerjaarunika.kerjaloka.ui.WelcomingPage.OnBoarding

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.google.android.material.button.MaterialButton
import java.util.*


data class OnBoardingItem(
    val id: Int,
    val onboardingImage: Int,
    val title: String,
    val description: String,
)

class OnBoardingItemAdapter(private val onboardingItems: List<OnBoardingItem>) :
RecyclerView.Adapter<OnBoardingItemAdapter.OnBoarding>()
{

    inner class OnBoarding(view: View, context: Context) : RecyclerView.ViewHolder(view){

        private val imageOnBoarding= view.findViewById<ImageView>(R.id.imageOnBoarding)
        private val Title = view.findViewById<TextView>(R.id.Title)
        private val Description = view.findViewById<TextView>(R.id.Description)
        private val Mulai = view.findViewById<MaterialButton>(R.id.mulai)

        fun bind(onboardingItem: OnBoardingItem){
            imageOnBoarding.setImageResource(onboardingItem.onboardingImage)
            Title.text = onboardingItem.title
            Description.text = onboardingItem.description
            if(onboardingItem.id==4){
                Mulai.isVisible = true

            }
//            Mulai.setOnClickListener(object: View.OnClickListener) {
//                fun onClick(view: View): Unit {
//                    val intent = Intent(context, MainActivity::class.java);
//                    startActivity(intent);
//                }
//            })
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoarding {
        val view = OnBoarding(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_container,
                parent,
                false
            )
        )

        return view;
    }

    override fun onBindViewHolder(holder: OnBoarding, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }
}

