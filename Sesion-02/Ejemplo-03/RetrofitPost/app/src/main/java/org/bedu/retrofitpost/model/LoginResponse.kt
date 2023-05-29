package org.bedu.retrofitpost.model

data class LoginResponse(
    val token: String,
    val error: String
)