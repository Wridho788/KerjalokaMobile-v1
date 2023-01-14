package com.ciptakerjaarunika.kerjaloka.viewmodel.Components.Camera
import android.app.Activity
import android.content.Intent
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerConfiguration
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerView
import com.ciptakerjaarunika.kerjaloka.entity.CameraResult

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ActivityPickerViewController private constructor() : ICustomPickerView {

    private var publishSubject: PublishSubject<CameraResult> = PublishSubject.create()

    private lateinit var activityClass: Class<out Activity>

    fun setActivityClass(clazz: Class<out Activity>) {
        activityClass = clazz
    }

    fun resetSubject() {
        publishSubject = PublishSubject.create()
    }

    override fun display(fragmentActivity: androidx.fragment.app.FragmentActivity,
                         viewContainer: Int,
                         configuration: ICustomPickerConfiguration?) {
        resetSubject()
        fragmentActivity.startActivity(Intent(fragmentActivity, activityClass))
    }

    override fun pickImage(): Observable<CameraResult> {
        return publishSubject
    }

    fun emitResult(result: CameraResult) {
        publishSubject.onNext(result)
    }

    fun emitResults(results: List<CameraResult>) {
        for (result in results) {
            emitResult(result)
        }
    }

    fun emitError(e: Throwable) {
        publishSubject.onError(e)
    }

    fun endResultEmitAndReset() {
        publishSubject.onComplete()
        resetSubject()
    }

    companion object {

        @Volatile
        private var INSTANCE: ActivityPickerViewController? = null

        val instance: ActivityPickerViewController
            get() {
                if (INSTANCE == null) {
                    synchronized(ActivityPickerViewController::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = ActivityPickerViewController()
                        }
                    }
                }
                return INSTANCE!!
            }
    }
}