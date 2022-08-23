package com.ciptakerjaarunika.kerjaloka.ui.NotificationPage.Model

import java.time.LocalDateTime

data class Model(
    val read: Int,
    val title: String,
    val desc: String,
    val time: LocalDateTime,
    val img: Int

    )