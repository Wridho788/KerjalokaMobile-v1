package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model

import java.time.LocalDateTime

data class minat_model(
    val code: Int,
    val Preference: String,
)

data class js_profile(
    val userNo: Long=20211102115301,
    val jsNo: Int=47,
    var jobseekerName: String="Egi Fernandes Bangun Hutabarat Sianturi",
    val dateOfBirth: String= "2000-01-13T00:00:00",
    val noExperience: Boolean=false,
    val jsGender: String="M"
)

data class add_Info(
    val ethnics: String= "Batak",
    val expectedSalary: Int= 0,
    val instagramId: String= null.toString(),
    val jobseekerAbout: String="Bukan WIBU",
    val jobseekerCityNo: Int= 479,
    val jobseekerCountryNo: Int= 192,
    val jobseekerCurrentAddress: String= "Jl. Bareng Yuk",
    val jobseekerNo: Long= 20211102115301,
    val jobseekerProvinceNo: Int= 25,
    val ktp: String = "1271031401000222",
    val ktpImage: String= "2021110211530133881.png",
    val maritalNo: Int= 1,
    val photo: String= "20211102115301547.png",
    val placeOfBirth: String= "Medan",
    val postalCode: String= "12312",
    val religionNo: Int= 2,
    val residentNo: Int= 1,
    val telegramId: String="medan",
    val vaccinated: Boolean=false
)

data class city(
    val cityName: String= "Kota Medan",
    val cityNo: Int= 479,
    val cityProvinceNo: Int= 25
)

data class country(
    val countryName: String= "Indonesia",
    val countryNo: Int= 192
)

data class marital(
    val maritalName: String= "Belum Menikah",
    val maritalNo: Int= 1
)

data class province(
    val provinceCountryNo: Int= 192,
    val provinceName: String="Sumatera Utara",
    val provinceNo: Int= 25
)

data class religion(
    val religionName: String="Protestan",
    val religionNo: Int=2
)

data class resident(
    val residentName: String= "Warga Negara Asli",
    val residentNo: Int=1
)

data class skills(
    val jobseekerNo: Long,
    val scale: Int,
    val skillName: String,
    val skillNo: Int
)