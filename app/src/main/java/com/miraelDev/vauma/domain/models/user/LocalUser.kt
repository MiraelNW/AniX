package com.miraelDev.vauma.domain.models.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class LocalUser(
    val email: String,
)
