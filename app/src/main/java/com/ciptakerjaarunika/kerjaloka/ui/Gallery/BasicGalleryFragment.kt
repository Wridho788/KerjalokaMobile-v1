package com.ciptakerjaarunika.kerjaloka.ui.Gallery

import android.content.Intent
import android.net.Uri
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerConfiguration
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerView
import com.ciptakerjaarunika.kerjaloka.entity.CameraResult
import com.ciptakerjaarunika.kerjaloka.ui.Camera.BaseSystemPickerFragment
import io.reactivex.subjects.PublishSubject
import io.reactivex.Observable


class BasicGalleryFragment : BaseSystemPickerFragment(), ICustomPickerView {

    private var mDefaultSystemGalleryConfig: DefaultSystemGalleryConfig? = null

    override fun display(fragmentActivity: FragmentActivity,
                         @IdRes viewContainer: Int,
                         configuration: ICustomPickerConfiguration?) {
        injectConfigurations(configuration)

        val fragmentManager = fragmentActivity.supportFragmentManager
        val fragment: androidx.fragment.app.Fragment? = fragmentManager.findFragmentByTag(tag)
        val transaction = fragmentManager.beginTransaction()
        if (fragment != null) {
            transaction.remove(fragment)
        }
        if (viewContainer != 0) {
            transaction.add(viewContainer, this, tag)
        } else {
            transaction.add(this, tag)
        }
        transaction.commitAllowingStateLoss()
    }

    private fun injectConfigurations(configuration: ICustomPickerConfiguration?) {
        when (configuration) {
            is DefaultSystemGalleryConfig ->
                this.mDefaultSystemGalleryConfig = configuration
            else -> {
                // do nothing
            }
        }
    }

    override fun pickImage(): Observable<CameraResult> {
        publishSubject = PublishSubject.create<CameraResult>()
        return uriObserver
    }

    override fun startRequest() {
        if (!checkPermission()) {
            return
        }

        val mTemp = mDefaultSystemGalleryConfig ?: DefaultSystemGalleryConfig.defaultInstance()
        mTemp.apply {
            startActivityForResult(this.systemIntent(), BaseSystemPickerFragment.GALLERY_REQUEST_CODE)
        }
    }

    override fun getActivityResultUri(data: Intent?): Uri? {
        return data?.data
    }
}