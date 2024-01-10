package com.miraeldev.account.domain

import androidx.compose.runtime.Stable

@Stable
data class UserModel(
    val id: Long,
    val username: String,
    val name: String,
    val image: String,
    val email: String,
){
    companion object{
        val Empty = UserModel(-1, "", "", "", "")
    }
}