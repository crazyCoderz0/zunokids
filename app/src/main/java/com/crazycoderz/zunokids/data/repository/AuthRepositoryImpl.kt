package com.crazycoderz.zunokids.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.crazycoderz.zunokids.data.model.AuthUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: Flow<AuthUser?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser?.let {
                AuthUser(id = it.uid, email = it.email ?: "", displayName = it.displayName)
            }
            trySend(user)
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose { firebaseAuth.removeAuthStateListener(authStateListener) }
    }

    override suspend fun login(email: String, password: String): Result<AuthUser> = try {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        val user = result.user?.let {
            AuthUser(id = it.uid, email = it.email ?: "", displayName = it.displayName)
        }
        if (user != null) Result.success(user) else Result.failure(Exception("Authentication failed"))
    } catch (e: Exception) {
        Result.failure(mapFirebaseException(e))
    }

    override suspend fun signUp(email: String, password: String): Result<AuthUser> = try {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user?.let {
            AuthUser(id = it.uid, email = it.email ?: "", displayName = it.displayName)
        }
        if (user != null) Result.success(user) else Result.failure(Exception("Registration failed"))
    } catch (e: Exception) {
        Result.failure(mapFirebaseException(e))
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    private fun mapFirebaseException(e: Exception): Exception {
        return when (e) {
            is FirebaseAuthInvalidUserException -> Exception("No account found with this email.")
            is FirebaseAuthInvalidCredentialsException -> Exception("Invalid credentials. Please check your email and password.")
            is FirebaseAuthUserCollisionException -> Exception("An account already exists with this email.")
            is FirebaseAuthWeakPasswordException -> Exception("Password is too weak. Use at least 6 characters.")
            is IOException -> Exception("Network error. Please check your internet connection.")
            else -> Exception(e.localizedMessage ?: "An unexpected error occurred.")
        }
    }
}
