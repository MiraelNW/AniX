package com.miraelDev.vauma.domain.models

data class Similar(
    val id: Int,
    val kind: String,
    val nameEn: String,
    val image: String,
    val score: Float,
    val nameRu: String,
)