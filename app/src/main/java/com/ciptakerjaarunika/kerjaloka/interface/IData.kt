package com.ciptakerjaarunika.kerjaloka.`interface`

import androidx.annotation.IdRes
import com.ciptakerjaarunika.kerjaloka.entity.CameraResult
import io.reactivex.Observable

interface iUpdateMajor {
    fun updateMajor(value : Int?)
}

interface iRefreshData {
    fun refresh()
}

interface iCloseModal {
    fun close()
}

interface iUpdateTitle {
    fun updateTitle(value : Int?)
}