package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.Listener

import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.Profile.ReviewSaya.Model.DataX

interface ShowModal {
    fun showDetail(review: DataX)
    fun showDelete(review: DataX)
}