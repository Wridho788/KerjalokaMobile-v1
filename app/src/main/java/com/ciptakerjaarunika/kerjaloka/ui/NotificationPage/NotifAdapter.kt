package com.ciptakerjaarunika.kerjaloka.ui.NotificationPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.Model
import com.ciptakerjaarunika.kerjaloka.R

class NotifAdapter(private val notifList: ArrayList<Model>, val context: Context) :
    RecyclerView.Adapter<NotifAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val Img = view.findViewById<ImageView>(R.id.icNotif)
        private val Title = view.findViewById<TextView>(R.id.notifTitle)
        private val Description = view.findViewById<TextView>(R.id.notifDesc)
        private val Time = view.findViewById<TextView>(R.id.notifTime)

        fun bindItems(model: Model){
            Img.setImageResource(model.img)
            Title.text = model.title
            Description.text = model.desc
            Time.text = model.time.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.notif_card,
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(notifList[position])
    }

    override fun getItemCount(): Int {
        return notifList.size
    }
}