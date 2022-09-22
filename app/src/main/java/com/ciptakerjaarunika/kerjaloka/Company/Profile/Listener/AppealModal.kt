package com.ciptakerjaarunika.kerjaloka.Company.Profile.Listener

import com.ciptakerjaarunika.kerjaloka.Company.Package.history_modal
import com.ciptakerjaarunika.kerjaloka.Company.Package.pack
import com.ciptakerjaarunika.kerjaloka.Company.Profile.review
import com.ciptakerjaarunika.kerjaloka.ui.Screens.CompanyReview.Model.reviewList

interface AppealModal {
    fun appealModal(review: reviewList)
}