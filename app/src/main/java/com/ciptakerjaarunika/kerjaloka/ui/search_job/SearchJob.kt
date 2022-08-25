package com.ciptakerjaarunika.kerjaloka.ui.search_job

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.databinding.ActivitySearchJobBinding
import com.google.android.material.chip.Chip


class SearchJob : AppCompatActivity(), View.OnKeyListener {

    private lateinit var binding: ActivitySearchJobBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchJob.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty() == true){
                    newChips(query)
                }
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    newChips(newText)
                }
                return false
            }
        })

        binding.searchJob.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_SEARCH || event.action == KeyEvent.KEYCODE_SEARCH){
                binding.apply {
                    val search = searchJob.query.toString()
                    newChips(search)
                    searchJob.setQuery("", false)
                    searchJob.clearFocus()
                }
            }
            false
        }
        createChips()

    }

    private fun createChips(){


    }

    private fun newChips(name: String){
        val chip = Chip(this)
        chip.apply {
            text=name
            chipIcon=ContextCompat.getDrawable(
                this@SearchJob,
                R.drawable.ic_search
            )
            isChipIconVisible=false
            isCloseIconVisible=true
            isClickable=true
            isCheckable=true
            binding.apply {
                chipGroup.addView(chip as View)
                chip.setOnCloseIconClickListener {
                    chipGroup.removeView(chip as View)
                }
            }
        }
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }
}