package com.ciptakerjaarunika.kerjaloka.entity

import androidx.annotation.IdRes
import com.ciptakerjaarunika.kerjaloka.ui.Gallery.BasicGalleryFragment
import kotlin.reflect.KClass

/**
 * This annotation will be marked open Gallery，it will conflict with [Camera]
 */
@Retention
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class Gallery(

    val componentClazz: KClass<*> = BasicGalleryFragment::class,

    val openAsFragment: Boolean = true,

    @IdRes val containerViewId: Int = 0
)