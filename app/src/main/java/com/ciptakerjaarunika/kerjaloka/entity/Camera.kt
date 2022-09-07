package com.ciptakerjaarunika.kerjaloka.entity

import androidx.annotation.IdRes
import com.ciptakerjaarunika.kerjaloka.ui.Camera.BasicCameraFragment
import kotlin.reflect.KClass

/**
 * This annotation will be marked open Gallery，it will conflict with [Gallery]
 */
@Retention
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class Camera(
    val componentClazz: KClass<*> = BasicCameraFragment::class,

    val openAsFragment: Boolean = true,

    @IdRes val containerViewId: Int = 0
)