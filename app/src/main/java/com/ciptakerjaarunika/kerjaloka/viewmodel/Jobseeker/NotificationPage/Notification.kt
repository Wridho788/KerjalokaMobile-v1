package com.ciptakerjaarunika.kerjaloka.viewmodel.Jobseeker.NotificationPage

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.ui.contextmenu.Item
import com.ciptakerjaarunika.kerjaloka.api.UsersAPI
import com.ciptakerjaarunika.kerjaloka.databinding.ActivityNotificationBinding
import com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage.Model.CompanyNotificationModel
import com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage.item.CompanyItemSectionDecoration
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.AddTrace

class Notification : AppCompatActivity() {

    private lateinit var adapter: NotifAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var itemSectionDecoration: CompanyItemSectionDecoration
    private lateinit var binding: ActivityNotificationBinding
    private var notificationsList : List<CompanyNotificationModel> = listOf()

    @AddTrace(name="onNotificationPageTrace", enabled = true)
    class ItemCache{
        fun fetch(name: String): Item? {
            return null
        }
    }

    fun NotificationTrace() {
        val cache = ItemCache()
        val myTrace = Firebase.performance.newTrace("notification_trace")
        myTrace.start()
        val item = cache.fetch("item")
        if (item != null) {
            myTrace.incrementMetric("item_cache_hit", 1)
        } else {
            myTrace.incrementMetric("item_cache_miss", 1)
        }
        myTrace.stop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationTrace()
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val thisActivity = this

        (thisActivity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (thisActivity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnBackJob.setOnClickListener{
            finish()
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
