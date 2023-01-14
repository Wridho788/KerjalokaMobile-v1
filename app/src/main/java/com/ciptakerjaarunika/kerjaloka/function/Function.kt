package com.ciptakerjaarunika.kerjaloka.function

import android.net.Uri
import com.ciptakerjaarunika.kerjaloka.entity.CameraResult

fun parseResultNoExtraData(uri: Uri): CameraResult {
    return CameraResult.Builder(uri).build()
}