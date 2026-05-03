package com.crazycoderz.zunokids.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
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
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
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

    override suspend fun reauthenticate(password: String): Result<Unit> = try {
        val user = firebaseAuth.currentUser ?: throw Exception("No user logged in")
        val email = user.email ?: throw Exception("User email not found")
        val credential = EmailAuthProvider.getCredential(email, password)
        
        user.reauthenticate(credential).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(mapFirebaseException(e))
    }

    override suspend fun deleteAccount(): Result<Unit> = try {
        val user = firebaseAuth.currentUser ?: throw Exception("No user logged in")
        val userId = user.uid

        // BEST PRACTICE: Delete Firestore document FIRST while authenticated
        firestore.collection("users").document(userId).delete().await()

        // THEN delete the Auth user
        user.delete().await()

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(mapFirebaseException(e))
    }

    private fun mapFirebaseException(e: Exception): Exception {
        return when (e) {
            is FirebaseAuthRecentLoginRequiredException -> Exception("Recent login required. Please log in again before deleting your account.")
            is FirebaseAuthInvalidUserException -> Exception("No account found.")
            is FirebaseAuthInvalidCredentialsException -> Exception("Invalid password.")
            is IOException -> Exception("Network error. Please check your connection.")
            else -> Exception(e.localizedMessage ?: "An unexpected error occurred.")
        }
    }
}
