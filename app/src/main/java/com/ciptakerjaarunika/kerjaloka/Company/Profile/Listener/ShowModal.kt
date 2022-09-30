package com.ciptakerjaarunika.kerjaloka.Company.Profile.Listener

import com.ciptakerjaarunika.kerjaloka.Company.Profile.ReviewSaya.Model.DataX
import com.ciptakerjaarunika.kerjaloka.Company.Profile.myReview

interface ShowModal {
    fun showDetail(review: DataX)
    fun showDelete(review: DataX)
}