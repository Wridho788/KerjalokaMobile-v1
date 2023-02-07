package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.Adapter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.enum.PackageType
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.Listener.ShowModalHistory
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.Model.Data
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class myPackageAdapter(private val context: Context, private val PackageList: List<Data>, private val listener: ShowModalHistory
):
    RecyclerView.Adapter<myPackageAdapter.myPackage>()
{
        inner class myPackage(view: View) : RecyclerView.ViewHolder(view){
            var pckName: TextView
            var pckType: TextView
            var credit: TextView
            var startOn: TextView
            var exp: TextView
            var img: ImageView
            var seeHistory: MaterialButton

            init {
                pckName = view.findViewById(R.id.packageName)
                pckType = view.findViewById(R.id.packageType)
                credit = view.findViewById(R.id.credit)
                startOn = view.findViewById(R.id.PackagetStartOn)
                exp = view.findViewById(R.id.PackageExpiredOn)
                img = view.findViewById(R.id.img_package)
                seeHistory = view.findViewById(R.id.btn_pckHistory)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myPackage {
        val view = View.inflate(parent.context, R.layout.comp_package_card, null)
//        val btn_seeHistory = view.findViewById<MaterialButton>(R.id.btn_pckHistory)
        return myPackage(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: myPackage, position: Int) {
        val currentItem = PackageList[position]
        holder.pckName.text= currentItem.packageX.packageName
        holder.pckType.text=currentItem.packageX.packageTypeNo.toString()
        if( currentItem.packageX.packageTypeNo == PackageType.JobPosting.value) {
            holder.img.setImageResource(R.drawable.ic_job_package)
        } else if (currentItem.packageX.packageTypeNo == PackageType.Certification.value) {
            holder.img.setImageResource(R.drawable.ic_certi_package)
        } else if (currentItem.packageX.packageTypeNo == PackageType.Test.value) {
            holder.img.setImageResource(R.drawable.ic_test_package)
        } else if(currentItem.packageX.packageTypeNo === PackageType.SearchCV.value) {
            holder.img.setImageResource(R.drawable.ic_job_offer_package)
        }
        val FormatStartOn = LocalDateTime.parse(currentItem.startOn.toString())
        val FormatExpiredOn = LocalDateTime.parse(currentItem.expiredOn.toString())
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

        val outputStarton = formatter.format(FormatStartOn)
        val outputExpiredOn = formatter.format(FormatExpiredOn)
        holder.credit.text=currentItem.packageX.packageCredit.toString()
        holder.startOn.text=outputStarton
        holder.exp.text=outputExpiredOn
        holder.seeHistory.setOnClickListener {
            listener.showDetail(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return  PackageList.size
    }

    class BottomsheetDialog : BottomSheetDialogFragment() {
        @Nullable
        override fun onCreateView(
            inflater: LayoutInflater,
            @Nullable container: ViewGroup?,
            @Nullable savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_history_modal, container, false)
        }
    }


}