package com.ciptakerjaarunika.kerjaloka.ui.JobPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter.RecommendationJobAdapter

class JobPage : AppCompatActivity() {

    private lateinit var adapter: RecommendationJobAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_page)
    }

    private fun onViewCreated(view: View, savedInstanceState: Bundle?){

    }
}