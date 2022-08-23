package com.ciptakerjaarunika.kerjaloka

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.AkunPage.AkunPage
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomePage
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewPage
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.LamaranPage

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomePage())


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment((HomePage()))
                R.id.lamaran -> replaceFragment((LamaranPage()))
                R.id.interview -> replaceFragment((InterviewPage()))
                R.id.akun -> replaceFragment((AkunPage()))
                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}