package com.crazycoderz.zunokids.data.model

data class AuthUser(
    val id: String,
    val email: String,
    val displayName: String? = null
)
