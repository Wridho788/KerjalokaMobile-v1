package com.ciptakerjaarunika.kerjaloka.Company.Profile

import com.ciptakerjaarunika.kerjaloka.model.Profile.JobseekerProfile

data class CompanyProfileResponse(
    val code : Int,
    val data : data,
    val message : String?
)

data class user(
    val company: company,
    val companyAdditional: compAdditional,
    val createdBy: Long = 0,
    val createdOn: String = "2021-10-27T21:10:22",
    val deactivated: Boolean = false,
    val email: String = "reyhan@kerjaloka.com",
    val emergencyPhone: String? = null,
    val isDiscoverable: Boolean = false,
    val isNewsletter: Boolean = true,
    val jobseekerAdditional: String? = null,
    val jobseekers: Long? = null,
    val phone: String = "082363153151",
    val roleNo: Int = 2,
    val rolePrevileges: role,
    val suspended: Boolean = false,
    val userFullname: String = "reyhan@kerjaloka.com",
    val userGoogleId: String = "113351807463143838932",
    val userNo: Long = 20211027141022,
    val username: String = "reyhan@kerjaloka.com"
)

data class company(
    val accountManager: Long? = null,
    val authorized: Boolean = true,
    val authorizedUserNo: Long = 0,
    val companyName: String = "TESTING",
    val companyNickName: String? = null,
    val companyNo: Long = 31,
    val userNo: Long = 20211027141022
)

data class compAdditional(
    val businessLicenseNumber: String = "2121212121212",
    val companyAddress: String? = null,
    val companyCeo: String = "KAMI",
    val companyCityNo: Int = 312,
    val companyCountryNo: Int = 192,
    val companyDescription: String = "Testing",
    val companyNo: Long = 20211027141022,
    val companyProvinceNo: Int = 11,
    val companyTypeNo: Int? = null,
    val fieldNo: Int = 4,
    val foundedAt: String = "1950-01-01T00:00:00",
    val ktp: String = "$2a$11\$hMpDzITmZhAMsy2YbYR5yOoMM5R56X4Fr10jK4U1GNp0mCG2Fsruy",
    val logo: String = "202110271410221246.jpg",
    val sizeNo: Int = 6,
)

data class role(
    val id: Int
)

data class data(
    val userNo: Long,
    val email: String,
    val phone: String?,
    val username: String,
    val userFullname: String,
    val companyName: String,
    val companyAddress: String?,
    val foundedAt: String,
    val city: city,
    val province: province,
    val country: country,
    val companyCeo: String,
    val field: field,
    val size: size,
    val logo: String,
    val companyDescription: String
)

data class city(
    val cityName: String = "Kabupaten Merauke",
    val cityNo: Int = 312,
    val cityProvinceNo: Int = 11
)

data class country(
    val countryName: String = "Indonesia",
    val countryNo: Int = 192
)

data class field(
    val fieldName: String = "Arts",
    val fieldNo: Int = 4,
    val fieldParentNo: Long? = null
)

data class province(
    val provinceCountryNo: Int,
    val provinceName: String,
    val provinceNo: Int
)

data class size(
    val sizeName: String = "500+",
    val sizeNo: Int = 6
)

data class review(
    val approvedByUserNo: Int = 0,
    val approvedOn: String = "2022-07-18T09:27:36",
    val canAppeal: Boolean = true,
    val comment: String = "asdasd",
    val conRating: ArrayList<conRat>,
    val ownerInfo:String,
    val proRating: ArrayList<proRat>,
    val raterPhoto: String = "202110271410221246.jpg",
    val rating: Int = 4,
    val ratingAt: String = "2022-07-18T09:27:20",
    val userFullName: String = "Kevin Hot Marojahan",
    val userNo: Long = 20211102115301,
    val userRatingNo: Int = 3,
    val userRole: Int = 2,
)

data class conRat(
    val id: Int = 0,
    val con: String = "Kemauan Bekerja",
)

data class proRat(
    val id: Int = 0,
    val con: String = "Disiplin",
)
