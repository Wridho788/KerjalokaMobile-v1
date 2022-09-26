package com.ciptakerjaarunika.kerjaloka.enum

enum class ApplicanStatusType(val value: Int, name : String ) {
    Applied(1, "Applied"),
    ShortList(2, "ShortList"),
    Test(3, "Test"),
    Interview(4,"Interview"),
    Accepted(5,"Accepted"),
    Rejected(6, "Rejected"),
    CVBank(7, "CV Bank"),
    Withdrawn(8, "Withdrawn")

}