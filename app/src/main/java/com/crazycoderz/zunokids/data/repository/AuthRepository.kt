package com.crazycoderz.zunokids.data.repository

import com.crazycoderz.zunokids.data.model.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<AuthUser?>
    suspend fun login(email: String, password: String): Result<AuthUser>
    suspend fun signUp(email: String, password: String): Result<AuthUser>
    suspend fun signOut()
    suspend fun deleteAccount(): Result<Unit>
    suspend fun reauthenticate(password: String): Result<Unit>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
}
