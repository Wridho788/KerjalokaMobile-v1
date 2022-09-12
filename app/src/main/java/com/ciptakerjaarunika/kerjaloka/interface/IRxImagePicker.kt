package com.ciptakerjaarunika.kerjaloka.`interface`

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.entity.Camera
import com.ciptakerjaarunika.kerjaloka.entity.CameraResult
import com.ciptakerjaarunika.kerjaloka.entity.Gallery
import com.ciptakerjaarunika.kerjaloka.entity.ProxyProviders
import com.ciptakerjaarunika.kerjaloka.ui.Gallery.DefaultSystemGalleryConfig
import io.reactivex.Observable
import java.lang.reflect.Proxy

interface BasicImagePicker {

    @Gallery
    fun openGallery(context: Context, instance: DefaultSystemGalleryConfig): Observable<CameraResult>

    @Camera
    fun openCamera(context: Context): Observable<CameraResult>
}

object RxImagePicker {

    @JvmStatic
    fun create(): BasicImagePicker {
        return create(BasicImagePicker::class.java)
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun <T> create(classProviders: Class<T>): T {
        val proxyProviders = ProxyProviders()

        return Proxy.newProxyInstance(
            classProviders.classLoader,
            arrayOf<Class<*>>(classProviders),
            proxyProviders) as T
    }
}