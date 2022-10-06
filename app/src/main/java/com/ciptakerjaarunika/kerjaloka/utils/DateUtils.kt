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
        val dateValue =  GetDateValue(value)
        val diffMinute = GetDiffMinute(LocalDateTime.now().toString(), value)

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
    open fun GetDiffMinute(start : String, end : String): Int {
        val date1 = GetDateValue(start).time
        val date2 = GetDateValue(end).time
        return if (date1 > date2) ((date1 - date2)/60000).toInt() else ((date2 - date1)/60000).toInt()
    }
    open fun GetDiffMonth(start : String, end : String): Int {
        val date1 = GetDateValue(start).time
        val date2 = GetDateValue(end).time
        return if (date1 > date2) ((date1 - date2)/2592000000).toInt() else ((date2 - date1)/2592000000).toInt()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    open fun GetHeaderMessage(value: String) : String {
        val dateValue =  GetDateValue(value)
        val diffMinute = GetDiffMinute(LocalDateTime.now().toString(), value)

        var Time = value.split("T")[1].split(":")
        if(diffMinute < 1440 && LocalDateTime.now().dayOfMonth == dateValue.date){
            return "Hari ini";
        }
        else if(diffMinute < 10080){
            return DateUtils().GetDayName(dateValue.day)
        }
        else{
            return LocalDateTime.parse(value).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        }
    }
    open fun GetDateValue(value: String) : Date{
        val temp = value.split("T")
        val time = temp[1].split(":")
        val date = "${temp[0]} ${time[0]}:${time[1]}"
        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return dateFormat.parse(date)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    open fun GetDateValueWithFormat(value: String?, format: String) : String{
        if(value == null) {return "-"}
        return LocalDateTime.parse(value).format(DateTimeFormatter.ofPattern(format))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    open fun GetTime(value : String): String {
        return LocalDateTime.parse(value).format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}