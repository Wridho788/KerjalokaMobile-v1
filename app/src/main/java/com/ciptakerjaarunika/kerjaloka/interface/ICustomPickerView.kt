package com.ciptakerjaarunika.kerjaloka.`interface`

import androidx.annotation.IdRes
import com.ciptakerjaarunika.kerjaloka.entity.CameraResult
import io.reactivex.Observable

interface ICustomPickerView {

    fun display(fragmentActivity: androidx.fragment.app.FragmentActivity,
                @IdRes viewContainer: Int,
                configuration: ICustomPickerConfiguration?)

    fun pickImage(): Observable<CameraResult>
}