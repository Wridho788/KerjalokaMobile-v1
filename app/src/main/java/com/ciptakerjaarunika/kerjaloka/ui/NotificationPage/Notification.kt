package com.ciptakerjaarunika.kerjaloka.ui.NotificationPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.Model
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.item.ItemSectionDecoration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class Notification : AppCompatActivity() {

//    private val scrollNotif: SwipeRefreshLayout by lazy{
//        findViewById(R.id.scrollNotif)
//    }

    private val notifContainer: RecyclerView by lazy{
        findViewById(R.id.notifContainer)
    }

    private lateinit var adapter: Adapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var itemSectionDecoration: ItemSectionDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        initList()

        reload()

//        val notifList = ArrayList<Model>()
//
//        notifList.add(Model("Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.of(2022, 8,15,20,20).toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model("Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
//
//
//
//
//        for(item in notifList.indices){
//
//            if (notifList[item].time < LocalDateTime.now().toString())
//            {
//                val notifAdapter2 = NotifAdapter(notifList, this)
//                val notifLayer2 = findViewById<RecyclerView>(R.id.notifContainerweek)
//                notifLayer2.layoutManager = LinearLayoutManager(this)
//                notifLayer2.adapter = notifAdapter2
//            }
//            else{
//                val notifAdapter1 = NotifAdapter(notifList, this)
//                val notifLayer1 = findViewById<RecyclerView>(R.id.notifContainertoday)
//                notifLayer1.layoutManager = LinearLayoutManager(this)
//                notifLayer1.adapter = notifAdapter1
//            }
//        }

//        if(notifList[].time < LocalDateTime.now()){
//            val notifAdapter2 = NotifAdapter(notifList, this)
//            val notifLayer2 = findViewById<RecyclerView>(R.id.notifContainerweek)
//            notifLayer2.layoutManager = LinearLayoutManager(this)
//            notifLayer2.adapter = notifAdapter2
//        }
//        else{
//            val notifAdapter1 = NotifAdapter(notifList, this)
//            val notifLayer1 = findViewById<RecyclerView>(R.id.notifContainertoday)
//            notifLayer1.layoutManager = LinearLayoutManager(this)
//            notifLayer1.adapter = notifAdapter1
//        }
    }

    private fun initList(){
//        scrollNotif.setOnRefreshListener {
//            scrollNotif.isRefreshing=false
//            reload()
//        }

        layoutManager= LinearLayoutManager(this)
        adapter = Adapter {
            loadMore()
        }

        itemSectionDecoration = ItemSectionDecoration(this){
            adapter.list
        }

        notifContainer.addItemDecoration(itemSectionDecoration)

        notifContainer.layoutManager = layoutManager
        notifContainer.adapter = adapter
    }

    private fun reload(){
        val list = dummyData(0,20)
        notifContainer.post {
            adapter.reload(list)
        }
    }

    private fun loadMore(){
        val list = dummyData(adapter.itemCount,15)
        notifContainer.post {
            adapter.loadMore(list)
        }
    }

    private fun dummyData(offset: Int, limit: Int): MutableList<Model>{
//        var list = mutableListOf<Model>()
//        var itemModel: Model
//        for(i in offset until offset + limit){
//            itemModel= when (i) {
//                in 0..15 ->{
//                    Model("title $i", getDummyDateString("01"))
//                }
//                in 15..30 ->{
//                    Model("title $i", getDummyDateString("02"))
//                }
//                else -> {
//                    Model("title $i", getDummyDateString("03"))
//                }
//            }
//            list.add(itemModel)
//        }
//        return list

        //==========================================================
        val notifList = mutableListOf<Model>()


        notifList.add(Model(0,"Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.of(2022, 8,14,20,20), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model(0, "Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,15,20,20), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model(0, "Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,2,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model(1, "Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model(1, "Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model(0,"Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.of(2022, 8,17,20,20), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model(0, "Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,15,20,20), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model(0, "Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,1,20,20), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model(1, "Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model(1, "Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model(0,"Lamaran Anda diTerima JUni", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.of(2022, 6,22,20,20), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model(0, "Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,15,20,20), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model(0, "Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,22, 15,30, 15, 98), R.drawable.kerjaloka_logo_small ))

        notifList.add(Model(0, "Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru",  LocalDateTime.of(2022, 8,22, 15,30, 2, 98), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model(1, "Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))
//        notifList.add(Model(1, "Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now(), R.drawable.kerjaloka_logo_small ))

        notifList.sortByDescending { it.time }

        return notifList
    }

//    private fun getDummyDateString(day: String): String{
//        return "2021-10-$day"
//    }
}