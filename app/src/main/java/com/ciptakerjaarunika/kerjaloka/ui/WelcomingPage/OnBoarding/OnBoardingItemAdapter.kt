package com.ciptakerjaarunika.kerjaloka.ui.WelcomingPage.OnBoarding

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.MainActivity
import com.ciptakerjaarunika.kerjaloka.R
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.android.awaitFrame


data class OnBoardingItem(
    val id: Int,
    val onboardingImage: Int,
    val title: String,
    val description: String,
)

class OnBoardingItemAdapter(private val onboardingItems: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingItemAdapter.OnBoarding>() {

    inner class OnBoarding(view: View, context: Context) : RecyclerView.ViewHolder(view) {

        private val imageOnBoarding = view.findViewById<ImageView>(R.id.imageOnBoarding)
        private val Title = view.findViewById<TextView>(R.id.Title)
        private val Description = view.findViewById<TextView>(R.id.Description)
        private val Mulai = view.findViewById<MaterialButton>(R.id.mulai)
        private val anim = AnimationUtils.loadAnimation( context, R.anim.slide)



        fun bind(onboardingItem: OnBoardingItem) {
            imageOnBoarding.setImageResource(onboardingItem.onboardingImage)
            Title.text = onboardingItem.title
            Description.text = onboardingItem.description
            Mulai.setOnClickListener(View.OnClickListener {
                val context=Mulai.context
                val intent = Intent( context, MainActivity::class.java)
                context.startActivity(intent)
            })
            do{
                if(onboardingItem.id==4){
                    Mulai.isVisible=false
                    Mulai.isVisible=true
                    Mulai.startAnimation(anim)
                }
                else{
                    Mulai.isVisible=false
                }
                break
            }while (
                onboardingItem.id>-1
            )

        }

//        do{
//            if(onboardingItem.id==4){
//                Mulai.isVisible=true
//                Mulai.startAnimation(anim)
//            }
//            else{
//                Mulai.isVisible=false
//            }
//            Mulai.isVisible=false
//        }
//        while (onboardingItem.id<5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoarding {
        return OnBoarding(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_container,
                parent,
                false
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: OnBoarding, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }
}

