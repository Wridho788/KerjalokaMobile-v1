package com.ciptakerjaarunika.kerjaloka.ui.NotificationPage

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityNotificationBinding
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.CompanyNotificationModel
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.item.CompanyItemSectionDecoration

class Notification : AppCompatActivity() {

    private lateinit var adapter: NotifAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var itemSectionDecoration: CompanyItemSectionDecoration
    private lateinit var binding: ActivityNotificationBinding
    private var notificationsList : List<CompanyNotificationModel> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val thisActivity = this

        (thisActivity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (thisActivity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnBackJob.setOnClickListener{
            thisActivity.onBackPressed()
        }
        initList()
        reload()


    }


    private fun initList() {
//        scrollNotif.setOnRefreshListener {
//            scrollNotif.isRefreshing=false
//            reload()
//        }

        layoutManager = LinearLayoutManager(this)
        adapter = NotifAdapter {
            loadMore()
        }

        itemSectionDecoration = CompanyItemSectionDecoration(this) {
            adapter.list
        }

        binding.notifContainer.addItemDecoration(itemSectionDecoration)

        binding.notifContainer.layoutManager = layoutManager
        binding.notifContainer.adapter = adapter
    }

    private fun reload() {
        UsersAPI().GetNotification(this) {
            if(it?.data != null) {
                binding.spinner.visibility = GONE
                binding.notifContainer.visibility = VISIBLE
                val list = it?.data?.sortedByDescending { it.createdOn }
//            val list = dummyData(0, 20)
                binding.notifContainer.post {
                    adapter.reload(list as MutableList<CompanyNotificationModel>)
                }
            }
        }
    }

    private fun loadMore() {
        UsersAPI().GetNotification(this) {
            binding.spinner.visibility = GONE
            binding.notifContainer.visibility = VISIBLE
            val list = it?.data?.sortedByDescending { it.createdOn }
//            val list = dummyData(adapter.itemCount, 15)
            binding.notifContainer.post {
                adapter.loadMore(list as MutableList<CompanyNotificationModel>)
            }
        }
    }
}
