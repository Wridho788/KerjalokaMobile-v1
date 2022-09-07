package com.ciptakerjaarunika.kerjaloka.`interface`

import io.reactivex.Scheduler

interface IRxImagePickerSchedulers {

    fun ui(): Scheduler

    fun io(): Scheduler
}