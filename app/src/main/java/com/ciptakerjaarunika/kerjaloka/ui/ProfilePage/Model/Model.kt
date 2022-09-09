package com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.Model

import java.time.LocalDateTime

data class minat_model(
    val code: Int,
    val Preference: String,
)

data class js_profile(
    val userNo: Long = 20211102115301,
    val jsNo: Int = 47,
    var jobseekerName: String = "Egi Fernandes Bangun Hutabarat Sianturi",
    val dateOfBirth: String = "2000-01-13T00:00:00",
    val noExperience: Boolean = false,
    val jsGender: String = "M"
)

data class add_Info(
    val ethnics: String = "Batak",
    val expectedSalary: Int = 0,
    val instagramId: String = null.toString(),
    val jobseekerAbout: String = "Bukan WIBU",
    val jobseekerCityNo: Int = 479,
    val jobseekerCountryNo: Int = 192,
    val jobseekerCurrentAddress: String = "Jl. Bareng Yuk",
    val jobseekerNo: Long = 20211102115301,
    val jobseekerProvinceNo: Int = 25,
    val ktp: String = "1271031401000222",
    val ktpImage: String = "2021110211530133881.png",
    val maritalNo: Int = 1,
    val photo: String = "20211102115301547.png",
    val placeOfBirth: String = "Medan",
    val postalCode: String = "12312",
    val religionNo: Int = 2,
    val residentNo: Int = 1,
    val telegramId: String = "medan",
    val vaccinated: Boolean = false
)

data class listCity(
    val city: String,
    val country: String,
    val locationsNo: Int,
    val province: String
)

data class city(
    val cityName: String = "Kota Medan",
    val cityNo: Int = 479,
    val cityProvinceNo: Int = 25
)

data class country(
    val countryName: String = "Indonesia",
    val countryNo: Int = 192
)

data class maritalStatus(
    val maritalNo: Int,
    val maritalName: String
)

data class marital(
    val maritalName: String = "Belum Menikah",
    val maritalNo: Int = 1
)

data class province(
    val provinceCountryNo: Int = 192,
    val provinceName: String = "Sumatera Utara",
    val provinceNo: Int = 25
)

data class religion(
    val religionName: String = "Protestan",
    val religionNo: Int = 2
)

data class skill(
    val skillName: String,
    val skillNo: Int
)

data class scale(
    val scaleName: String,
    val scaleNo: Int
)

data class religionList(
    val religionName: String,
    val religionNo: Int
)

data class residentList(
    val residentName: String,
    val residentNo: Int
)

data class resident(
    val residentName: String = "Warga Negara Asli",
    val residentNo: Int = 1
)

data class typeJob(
    val typeJob: String,
    val typeNo: Int
)

data class skills(
    val jobseekerNo: Long,
    val scale: Int,
    val skillName: String,
    val skillNo: Int
)

data class gender(
    val id: Int,
    val gender: String,
)

data class month(
    val id: Int,
    val month: String
)

data class year(
    val year: Int
)

data class majors(
    val majorName: String,
    val majorNo: Int
)

data class title(
    val id: Int,
    val Title: String
)

data class language(
    val languageName: String,
    val languageNo: Int
)

data class score(
    val score: Int
)

data class education(
    val educationBeginAt: String = "2019-02-01T00:00:00",
    val educationCityName: String = "Kota Langsa",
    val educationCityNo: Int = 2,
    val educationCountry: String = "Indonesia",
    val educationDescription: String = "",
    val educationEndedAt: String = "2021-01-01T00:00:00",
    val educationMajorName: String = "Agribusiness Operations",
    val educationMajorNo: Int = 160,
    val educationProvinceName: String = "Aceh",
    val educationSchool: String = "asd",
    val educationTitleName: String = "SMA/SMK",
    val educationTitleNo: Int = 1,
    val gpa: Int = 89,
    val jobseekerEducationNo: Int = 252,
    val jobseekerNo: Long = 20211102115301,
)

data class experience(
    val experienceBeginAt: String,
    val experienceCityName: String,
    val experienceCityNo: Int,
    val experienceCompanyName: String,
    val experienceCompanyNo: Int,
    val experienceCountry: String,
    val experienceDescription: String,
    val experienceEndedAt: String,
    val experienceJobTypeName: String,
    val experienceJobTypeNo: Int,
    val experienceNo: Int,
    val experiencePosition: String,
    val experienceProvinceName: String,
    val experienceSalary: Int,
    val jobseekerNo: Long,
)

data class languageList(
    val jobseekerNo: Long,
    val languageName: String,
    val languageNo: Int,
    val languageSpokenScale: Int,
    val languageWrittenScale: Int
)