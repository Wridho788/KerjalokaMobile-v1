package com.ciptakerjaarunika.kerjaloka.ui.Camera

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerConfiguration
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerView
import com.ciptakerjaarunika.kerjaloka.entity.CameraResult
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.text.SimpleDateFormat
import java.util.*

class BasicCameraFragment : BaseSystemPickerFragment(), ICustomPickerView {

    private var cameraPictureUrl: Uri? = null

    override fun display(fragmentActivity: FragmentActivity,
                         @IdRes viewContainer: Int,
                         configuration: ICustomPickerConfiguration?) {
        val fragmentManager = fragmentActivity.supportFragmentManager
        val fragment: androidx.fragment.app.Fragment? = fragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            val transaction = fragmentManager.beginTransaction()
            if (viewContainer != 0) {
                transaction.add(viewContainer, this, tag)
            } else {
                transaction.add(this, tag)
            }
            transaction.commitAllowingStateLoss()
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
        cameraPictureUrl = createImageUri()
        val pictureChooseIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        pictureChooseIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUrl)

        startActivityForResult(pictureChooseIntent, BaseSystemPickerFragment.CAMERA_REQUEST_CODE)
    }

    override fun getActivityResultUri(data: Intent?): Uri? {
        return cameraPictureUrl
    }

    private fun createImageUri(): Uri? {
        val contentResolver = requireActivity().contentResolver
        val cv = ContentValues()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        cv.put(MediaStore.Images.Media.TITLE, timeStamp)
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
    }
}