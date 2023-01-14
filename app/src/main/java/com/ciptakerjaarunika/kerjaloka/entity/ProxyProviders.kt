package com.ciptakerjaarunika.kerjaloka.entity

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerConfiguration
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerView
import com.ciptakerjaarunika.kerjaloka.`interface`.IRxImagePickerSchedulers
import com.ciptakerjaarunika.kerjaloka.viewmodel.Components.Camera.ActivityPickerViewController
import io.reactivex.*
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.concurrent.Callable
import kotlin.reflect.KClass
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers


class ProxyProviders : InvocationHandler {

    private val rxImagePickerProcessor = ConfigProcessor(
        RxImagePickerSchedulers()
    )
    private val proxyTranslator = ProxyTranslator()

    override fun invoke(proxy: Any, method: Method, args: Array<Any>?): Any {

        return Observable.defer(Callable<ObservableSource<*>> {
            val configProvider = proxyTranslator.processMethod(method, args)

            ImagePickerController(configProvider).display()

            val observable = rxImagePickerProcessor.process(configProvider)

            val methodType = method.returnType

            if (methodType == Observable::class.java)
                return@Callable Observable.just(observable)

            if (methodType == Single::class.java)
                return@Callable Observable.just<Single<*>>(Single.fromObservable(observable))

            if (methodType == Maybe::class.java)
                return@Callable Observable.just<Maybe<*>>(Maybe.fromSingle(Single.fromObservable(observable)))

            if (methodType == Flowable::class.java)
                return@Callable Observable.just(observable.toFlowable(BackpressureStrategy.MISSING))

            throw RuntimeException(method.name + " needs to return one of the next reactive types: observable, single, maybe or flowable")
        }).blockingFirst()
    }
}

data class ConfigProvider(val componentClazz: KClass<*>,
                          val asFragment: Boolean,
                          val sourcesFrom: CameraSourcesFrom,
                          @param:IdRes val containerViewId: Int,
                          /** runtime injection **/
                          val fragmentActivity: FragmentActivity,
                          val pickerView: ICustomPickerView,
                          val config: ICustomPickerConfiguration?)

class ConfigProcessor(private val schedulers: IRxImagePickerSchedulers) {

    fun process(configProvider: ConfigProvider): Observable<*> {
        return Observable.just(0)
            .flatMap {
                if (!configProvider.asFragment) {
                    return@flatMap ActivityPickerViewController.instance.pickImage()
                }
                when (configProvider.sourcesFrom) {
                    CameraSourcesFrom.GALLERY,
                    CameraSourcesFrom.CAMERA -> configProvider.pickerView.pickImage()
                }
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }
}

class RxImagePickerSchedulers : IRxImagePickerSchedulers {

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }
}
