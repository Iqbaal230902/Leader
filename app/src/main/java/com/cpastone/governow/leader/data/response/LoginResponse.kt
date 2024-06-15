package com.cpastone.governow.leader.data.response

import com.cpastone.governow.leader.data.model.User


data class LoginResponse(
    val error: Boolean,
    val message: String,
    val loginResult: User
)