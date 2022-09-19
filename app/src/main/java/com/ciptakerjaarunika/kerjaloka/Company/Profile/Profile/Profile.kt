package com.ciptakerjaarunika.kerjaloka.Company.Profile.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anychart.scales.DateTime
import com.ciptakerjaarunika.kerjaloka.Company.Profile.*
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model.record

class Profile : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val userL = ArrayList<user>()
        val compL = ArrayList<company>()
        val compAddL = ArrayList<compAdditional>()
        val dataL = ArrayList<data>()
        val countryL = ArrayList<country>()
        val cityL = ArrayList<city>()
        val fieldL = ArrayList<field>()
        val provinceL = ArrayList<province>()
        val sizeL = ArrayList<size>()
        val roleL = ArrayList<role>()

        val c1 = company(
            accountManager = null,
            authorized = true,
            authorizedUserNo = 0,
            companyName = "TESTING",
            companyNickName = null,
            companyNo = 31,
            userNo = 20211027141022
        )
        compL.add(c1)

        val cAdd1 = compAdditional(
            businessLicenseNumber = "2121212121212",
            companyAddress = null,
            companyCeo = "KAMI",
            companyCityNo = 312,
            companyCountryNo = 192,
            companyDescription = "Testing",
            companyNo = 20211027141022,
            companyProvinceNo = 11,
            companyTypeNo = null,
            fieldNo = 4,
            foundedAt = "1950-01-01T00:00:00",
            ktp = "$2a$11\$hMpDzITmZhAMsy2YbYR5yOoMM5R56X4Fr10jK4U1GNp0mCG2Fsruy",
            logo = "202110271410221246.jpg",
            sizeNo = 6,
        )
        compAddL.add(cAdd1)

        val dt1 = data(
            city = cityL,
            companyAddress = null,
            companyCeo = "KAMI",
            companyDescription = "Testing",
            companyName = "TESTING",
            country = countryL,
            email = "reyhan@kerjaloka.com",
            field = fieldL,
            foundedAt = "1950-01-01T00:00:00",
            logo = "202110271410221246.jpg",
            phone = "082363153151",
            province = provinceL,
            size = sizeL,
            userFullname = "reyhan@kerjaloka.com",
            userNo = 20211027141022,
            username = "reyhan@kerjaloka.com"
        )
        dataL.add(dt1)

        val ctry = country(
            countryName = "Indonesia",
            countryNo = 192
        )
        countryL.add(ctry)

        val ct1 = city(
            cityName = "Kabupaten Merauke",
            cityNo = 312,
            cityProvinceNo = 11
        )
        cityL.add(ct1)

        val fld = field(
            fieldName = "Arts",
            fieldNo = 4,
            fieldParentNo = null
        )
        fieldL.add(fld)

        val prv = province(
            provinceCountryNo = 192,
            provinceName = "Papua",
            provinceNo = 11
        )
        provinceL.add(prv)

        val sz = size(
            sizeName = "500+",
            sizeNo = 6
        )
        sizeL.add(sz)

        val usr1 = user(
            company = compL,
            companyAdditional = compAddL,
            createdBy = 0,
            createdOn = "2021-10-27T21:10:22",
            deactivated = false,
            email = "reyhan@kerjaloka.com",
            emergencyPhone = null,
            isDiscoverable = false,
            isNewsletter = false,
            jobseekerAdditional = null,
            jobseekers = null,
            phone = "082363153151",
            roleNo = 2,
            rolePrevileges = roleL,
            suspended = false,
            userFullname = "reyhan@kerjaloka.com",
            userGoogleId = "113351807463143838932",
            userNo = 20211027141022,
            username = "reyhan@kerjaloka.com"
        )
        userL.add(usr1)


        val txt_compLoc = view.findViewById<TextView>(R.id.compLoc)
        val txt_compAddress = view.findViewById<TextView>(R.id.compAddress)
        val txt_compPhone = view.findViewById<TextView>(R.id.compPhone)
        val txt_CEO = view.findViewById<TextView>(R.id.compCEO)
        val txt_since = view.findViewById<TextView>(R.id.since)
        val txt_compField = view.findViewById<TextView>(R.id.compField)
        val txt_compSize = view.findViewById<TextView>(R.id.compSize)
        val txt_compDesc = view.findViewById<TextView>(R.id.compDesc)

        txt_compLoc.text = dataL[0].province[0].provinceName + ", " + dataL[0].country[0].countryName
        txt_compAddress.text = compAddL[0].companyAddress
        txt_compPhone.text = dataL[0].phone
        txt_CEO.text = dataL[0].companyCeo
        txt_since.text = compAddL[0].foundedAt
        txt_compField.text = dataL[0].field[0].fieldName
        txt_compSize.text = dataL[0].size[0].sizeName
        txt_compDesc.text = compAddL[0].companyDescription


        return view
    }

    companion object {

    }
}