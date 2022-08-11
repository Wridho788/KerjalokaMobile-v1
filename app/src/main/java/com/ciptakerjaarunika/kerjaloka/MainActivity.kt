package com.ciptakerjaarunika.kerjaloka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityMainBinding
import com.ciptakerjaarunika.kerjaloka.ui.AkunPage.AkunFragment
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.HomeFragment
import com.ciptakerjaarunika.kerjaloka.ui.InterviewPage.InterviewFragment
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.LamaranFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment((HomeFragment()))
                R.id.lamaran -> replaceFragment((LamaranFragment()))
                R.id.interview -> replaceFragment((InterviewFragment()))
                R.id.akun -> replaceFragment((AkunFragment()))

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