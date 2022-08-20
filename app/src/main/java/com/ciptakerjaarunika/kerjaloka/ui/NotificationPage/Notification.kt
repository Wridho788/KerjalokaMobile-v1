package com.ciptakerjaarunika.kerjaloka.ui.NotificationPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.Model
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Notification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val notifList = ArrayList<Model>()

        notifList.add(Model("Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Lowongan Kerja Terbaru", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Event Pembagian Sembako", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Egi Fernandes Bangun", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))
        notifList.add(Model("Gelap Mata", "Lucu sekali dunia tipu tipu", LocalDateTime.now().toString(), R.drawable.kerjaloka_logo_small ))

        notifList.add(Model("Lamaran Anda diTerima", "Perusahaan yang anda ikuti baru saja mengunggah sebuah lowongan pekerjaan baru", LocalDateTime.of(2022, 8,15,20,20).minusDays().toString(), R.drawable.kerjaloka_logo_small ))





//        for(int i=notifList.size; i>0; i--){
//
//        }

//        if(notifList).time< LocalDateTime.now().toString()){
            val notifAdapter2 = NotifAdapter(notifList, this)
            val notifLayer2 = findViewById<RecyclerView>(R.id.notifContainerweek)
            notifLayer2.layoutManager = LinearLayoutManager(this)
            notifLayer2.adapter = notifAdapter2
//        }
//        else{
            val notifAdapter1 = NotifAdapter(notifList, this)
            val notifLayer1 = findViewById<RecyclerView>(R.id.notifContainertoday)
            notifLayer1.layoutManager = LinearLayoutManager(this)
            notifLayer1.adapter = notifAdapter1
//        }
    }
}