package com.ciptakerjaarunika.kerjaloka.Company.Profile

data class CompanyProfileResponse(
    val code : Int,
    val data : data,
    val message : String?
)

data class user_response(
    val code: Int,
    val data: user
)

data class user(
    val company: company?,
    val companyAdditional: compAdditional?,
    val createdBy: Long,
    val createdOn: String,
    val deactivated: Boolean,
    val email: String,
    val emergencyPhone: String?,
    val isDiscoverable: Boolean,
    val isNewsletter: Boolean,
    val jobseekerAdditional: ArrayList<String>?,
    val jobseekers: ArrayList<String>?,
    val phone: String,
    val roleNo: Int,
    val rolePrevileges: ArrayList<String>?,
    val suspended: Boolean,
    val userFullname: String,
    val userGoogleId: String,
    val userNo: Long,
    val username: String
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

data class ratingSended_response (
    val code: Int,
    val errorCode: Int,
    val message: String,
    val data: ratingData
)

data class ratingData (
    val userInfo: userInfo,
    val reviewList: List<myReview>
)

data class userInfo(
    val rating: Int,
    val email: String,
    val name: String,
    val ownerPhoto: String?
)

data class myReview(
    val userRatingNo: Long,
    val rating: Int,
    val ratingBy: Long,
    val ratingAt: String,
    val comment: String? = null,
    val userNo: Long,
    val userFullName: String,
    val raterPhoto: String? = null,
    val proRating: List<String>,
    val conRating: List<String>,
    val approved: Boolean,
    val approvedOn: String
)

data class ChangeUsernameRequest(
    val username: String
)

data class DeactivatedAccount(
    val password : String
)

data class CheckPhoneResponse(
    var exists: Boolean
)

data class CheckEmailResponse(
    val exists: Boolean
)

data class ChangeEmailRequest(
    val email: String
)

data class ChangePasswordRequest(
    val password: String,
    val newPassword: String
)



