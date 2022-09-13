package com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Package.pack
import com.ciptakerjaarunika.kerjaloka.R

class myPackageAdapter (private val PackageList: List<pack>):
    RecyclerView.Adapter<myPackageAdapter.myPackage>()
{
        inner class myPackage(view: View) : RecyclerView.ViewHolder(view){
            var pckName: TextView
            var pckType: TextView
            var credit: TextView
            var startOn: TextView
            var exp: TextView

            init {
                pckName = view.findViewById(R.id.packageName)
                pckType = view.findViewById(R.id.packageType)
                credit = view.findViewById(R.id.credit)
                startOn = view.findViewById(R.id.PackagetStartOn)
                exp = view.findViewById(R.id.PackageExpiredOn)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myPackage {
        val view = View.inflate(parent.context, R.layout.comp_package_card, null)
        return myPackage(view)
    }

    override fun onBindViewHolder(holder: myPackage, position: Int) {
        val currentItem = PackageList[position]
        holder.pckName.text= currentItem.packageName
        holder.pckType.text=currentItem.packageTypeNo.toString()
        holder.credit.text=currentItem.credit.toString()
        holder.startOn.text=currentItem.expiredOn
        holder.exp.text=currentItem.expiredOn
    }

    override fun getItemCount(): Int {
        return  PackageList.size
    }
}