package com.ciptakerjaarunika.kerjaloka.utils

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.LamaranPage.Model.ApplicantModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.Duration.Companion.minutes

class DateUtils {
     open fun GetDayName(value: Int): String {
        var dayName = ""
        when (value) {
            7 -> dayName = "Minggu"
            1 -> dayName = "Senin"
            2 -> dayName = "Selasa"
            3 -> dayName = "Rabu"
            4 -> dayName = "Kamis"
            5 -> dayName = "Jumat"
            6 -> dayName = "Sabtu"
        }
        return dayName
    }
    @RequiresApi(Build.VERSION_CODES.O)
    open fun GetLastMessageOn(value : String): String {
        val temp = value.split("T")
        val date = "${temp[0]} ${temp[1]}"

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        var dateValue = dateFormat.parse(date)

        val tempNow = LocalDateTime.now().toString().split("T")
        val dateNow = "${tempNow[0]} ${tempNow[1]}"
        val diffMinute = (dateFormat.parse(dateNow).time - dateValue.time)/6000

        var Time = value.split("T")[1].split(":")
        if(diffMinute < 1440 && LocalDateTime.now().dayOfMonth == dateValue.date){
            return "${Time[0]}:${Time[1]}";
        }
        else if(diffMinute < 10080){
            return DateUtils().GetDayName(dateValue.day)
        }
        else{
            return LocalDateTime.parse(value).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    open fun GetTime(value : String): String {
        return LocalDateTime.parse(value).format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}