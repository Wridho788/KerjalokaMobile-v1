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

//    private val scrollNotif: SwipeRefreshLayout by lazy{
//        findViewById(R.id.scrollNotif)
//    }

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
//            val list = dummyData(0, 20)
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
//            val list = dummyData(adapter.itemCount, 15)
            notifContainer.post {
                adapter.loadMore(list as MutableList<CompanyNotificationModel>)
            }
        }
    }

//
//    private fun dummyData(offset: Int, limit: Int): MutableList<CompanyNotificationModel> {
//        val notifList = mutableListOf<CompanyNotificationModel>()
//
//
//        notifList.add(CompanyNotificationModel(0,"Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.of(2022, 8,14,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(0, "Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,15,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(0, "Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,2,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(1, "Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(1, "Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(0,"Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.of(2022, 8,17,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(0, "Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,15,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(0, "Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,1,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(1, "Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(1, "Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(0,"Lamaran Anda diTerima JUni", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.of(2022, 6,22,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(0, "Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,15,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(0, "Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,22, 15,30, 15, 98), R.drawable.kerjaloka_logo_small ))
//
//        notifList.add(CompanyNotificationModel(0, "Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,22, 15,30, 2, 98), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(1, "Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(1, "Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(CompanyNotificationModel(0,"Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.of(2022, 8,14,20,20), R.drawable.kerjaloka_logo_small ))
//
//        notifList.sortByDescending { it.time }
//
//
//        return notifList
//    }
}


