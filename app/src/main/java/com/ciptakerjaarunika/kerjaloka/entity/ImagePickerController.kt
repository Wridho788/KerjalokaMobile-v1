package com.ciptakerjaarunika.kerjaloka.entity

import android.app.Activity
import androidx.annotation.IdRes
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerConfiguration
import com.ciptakerjaarunika.kerjaloka.`interface`.ICustomPickerView
import com.ciptakerjaarunika.kerjaloka.ui.Camera.ActivityPickerViewController
import io.reactivex.*
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.concurrent.Callable
import kotlin.reflect.KClass
class ImagePickerController(private val configProvider: ConfigProvider) {

    fun display() {
        configProvider.config?.onDisplay()

        if (!configProvider.asFragment)
            displayPickerViewAsActivity(configProvider.config)
        else
            displayPickerViewAsFragment(configProvider.config)
    }

    private fun displayPickerViewAsActivity(configuration: ICustomPickerConfiguration?) {
        val activityHolder = ActivityPickerViewController.instance
        activityHolder.setActivityClass(configProvider.componentClazz.java as Class<out Activity>)
        activityHolder.display(
            configProvider.fragmentActivity, configProvider.containerViewId, configuration
        )
    }

    private fun displayPickerViewAsFragment(configuration: ICustomPickerConfiguration?) {
        configProvider.pickerView.display(
            configProvider.fragmentActivity, configProvider.containerViewId, configuration
        )
    }
}