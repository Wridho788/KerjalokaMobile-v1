package com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityCompanyNotificationBinding
import com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage.Model.CompanyNotificationModel
import com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage.item.CompanyItemSectionDecoration

class CompanyNotification : AppCompatActivity() {

    private val notifContainer: RecyclerView by lazy {
        findViewById(R.id.notifContainer)
    }

    private lateinit var adapter: CompanyAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var itemSectionDecoration: CompanyItemSectionDecoration
    private lateinit var binding: ActivityCompanyNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyNotificationBinding.inflate(layoutInflater)

        binding.btnBackJob.setOnClickListener{
            finish()
        }
        setContentView(binding.root)
            initList()
            reload()
    }

    private fun initList() {
//        scrollNotif.setOnRefreshListener {
//            scrollNotif.isRefreshing=false
//            reload()
//        }

        layoutManager = LinearLayoutManager(this)
        adapter = CompanyAdapter {
            loadMore()
        }

        itemSectionDecoration = CompanyItemSectionDecoration(this) {
            adapter.list
        }

        notifContainer.addItemDecoration(itemSectionDecoration)

        notifContainer.layoutManager = layoutManager
        notifContainer.adapter = adapter
    }

    private fun reload() {
        UsersAPI().GetNotification(this) {
            val list = it?.data?.sortedByDescending { it.createdOn }
            binding.spinner.visibility = GONE
            binding.notifContainer.visibility = VISIBLE
            notifContainer.post {
                adapter.reload(list as MutableList<CompanyNotificationModel>)
            }
        }
    }

    private fun loadMore() {
        UsersAPI().GetNotification(this) {
            val list = it?.data?.sortedByDescending { it.createdOn }
            binding.spinner.visibility = GONE
            binding.notifContainer.visibility = VISIBLE
            notifContainer.post {
                adapter.loadMore(list as MutableList<CompanyNotificationModel>)
            }
        }
    }
}


