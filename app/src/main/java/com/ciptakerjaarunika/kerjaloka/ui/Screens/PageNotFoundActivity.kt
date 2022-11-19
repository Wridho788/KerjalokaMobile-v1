package com.ciptakerjaarunika.kerjaloka.ui.Screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityPageNotFoundBinding

class PageNotFoundActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPageNotFoundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPageNotFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

}