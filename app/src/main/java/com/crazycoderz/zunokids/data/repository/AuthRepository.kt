package com.crazycoderz.zunokids.data.repository

import com.crazycoderz.zunokids.data.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<AuthUser?>
    suspend fun login(email: String, password: String): Result<AuthUser>
    suspend fun signUp(email: String, password: String): Result<AuthUser>
    suspend fun signOut()
}
