package com.ciptakerjaarunika.kerjaloka.Company.Profile.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.Company.Profile.data
import com.ciptakerjaarunika.kerjaloka.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Profile(val data: data?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val txtcompLoc = view.findViewById<TextView>(R.id.compLoc)
        val txtcompAddress = view.findViewById<TextView>(R.id.compAddress)
        val txtcompPhone = view.findViewById<TextView>(R.id.compPhone)
        val txtCEO = view.findViewById<TextView>(R.id.compCEO)
        val txtsince = view.findViewById<TextView>(R.id.since)
        val txtcompField = view.findViewById<TextView>(R.id.compField)
        val txtcompSize = view.findViewById<TextView>(R.id.compSize)
        val txtcompDesc = view.findViewById<TextView>(R.id.compDesc)

        txtcompLoc.text = "${data?.province?.provinceName}, ${data?.country?.countryName}"
        txtcompAddress.text = data?.companyAddress
        txtcompPhone.text = data?.phone
        txtCEO.text = data?.companyCeo
        if (!data?.foundedAt.isNullOrEmpty() && !data?.foundedAt.isNullOrBlank()) {
            val parse = LocalDateTime.parse(data?.foundedAt).format(DateTimeFormatter.ofPattern("dd MMMM YYYY")).toString()
            txtsince.text = parse.toString()

        }
        txtcompField.text = data?.field?.fieldName
        txtcompSize.text = data?.size?.sizeName
        txtcompDesc.text = data?.companyDescription

        return view
    }
}