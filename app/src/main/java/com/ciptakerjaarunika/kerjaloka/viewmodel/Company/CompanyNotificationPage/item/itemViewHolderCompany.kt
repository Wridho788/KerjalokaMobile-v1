package com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage.item

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.viewmodel.NotificationPage.Model.CompanyNotificationModel
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ResourceAsColor")
class itemViewHolderCompany(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val view = WeakReference(itemView)
    private lateinit var card: RelativeLayout

    private var Title: TextView? = null
    private var Desc: TextView? = null
    private var Time: TextView? = null
    private var Image: ImageView? = null

    var itemModel: CompanyNotificationModel? = null

    init {
        view.get()?.let {
            Title = it.findViewById(R.id.notifTitle)
            Desc = it.findViewById(R.id.notifDesc)
            Time = it.findViewById(R.id.notifTime)
            Image = it.findViewById(R.id.icNotif)
            card = it.findViewById(R.id.cardNotif)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun updateView() {
        Title?.text = itemModel?.message
        Desc?.text = itemModel?.message

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        sdf.timeZone = TimeZone.getTimeZone("GMT+7")
        val time: Long = sdf.parse(itemModel?.createdOn).time
        val now = System.currentTimeMillis()
        val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)


        Time?.text = ago
        Image?.let {
            Glide.with(it.context)
                .load(config().portAddress + "photo/Profile/" + itemModel?.photo).fitCenter()
                .into(Image!!)
        }
        if (itemModel?.read == true) {
            card.setBackgroundColor(Color.parseColor("#fff1f1"))
        } else if (itemModel?.read == false) {
            card.setBackgroundColor(Color.parseColor("#ffffff"))
        }
    }
}