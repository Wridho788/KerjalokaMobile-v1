package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.Listener.ShowModalHistory
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Package.Model.Data
import com.ciptakerjaarunika.kerjaloka.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton


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
            var seeHistory: MaterialButton

            init {
                pckName = view.findViewById(R.id.packageName)
                pckType = view.findViewById(R.id.packageType)
                credit = view.findViewById(R.id.credit)
                startOn = view.findViewById(R.id.PackagetStartOn)
                exp = view.findViewById(R.id.PackageExpiredOn)
                seeHistory = view.findViewById(R.id.btn_pckHistory)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myPackage {
        val view = View.inflate(parent.context, R.layout.comp_package_card, null)
//        val btn_seeHistory = view.findViewById<MaterialButton>(R.id.btn_pckHistory)


        return myPackage(view)
    }

    override fun onBindViewHolder(holder: myPackage, position: Int) {
        val currentItem = PackageList[position]
        holder.pckName.text= currentItem.packageX.packageName
        holder.pckType.text=currentItem.packageX.packageTypeNo.toString()
        holder.credit.text=currentItem.packageX.packageCredit.toString()
        holder.startOn.text=currentItem.startOn
        holder.exp.text=currentItem.expiredOn

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