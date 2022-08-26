package com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.item

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model.Model
import java.lang.ref.WeakReference
import com.ciptakerjaarunika.kerjaloka.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

@SuppressLint("ResourceAsColor")
class itemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)
    private lateinit var card: RelativeLayout

    private var Title: TextView? = null
    private var Desc: TextView? = null
    private var Time: TextView? = null
    private var Image: ImageView? = null

    var itemModel: Model? = null

    init {
        view.get()?.let {
            Title = it.findViewById(R.id.notifTitle)
            Desc = it.findViewById(R.id.notifDesc)
            Time = it.findViewById(R.id.notifTime)
            Image = it.findViewById(R.id.icNotif)
            card = it.findViewById(R.id.cardNotif)
        }
    }

    fun updateView(){
        Title?.text = itemModel?.title
        Desc?.text = itemModel?.desc

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"))
            val time: Long = sdf.parse(itemModel?.time.toString()).getTime()
            val now = System.currentTimeMillis()
            val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)


        Time?.text = ago
        itemModel?.img?.let { Image?.setImageResource(it)
            if(itemModel?.read == 1){
                card.setBackgroundColor(Color.parseColor("#fff1f1"))
            }
            else if(itemModel?.read == 0){
                card.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }
    }
}