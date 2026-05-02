package com.crazycoderz.zunokids.di

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.data.repository.AuthRepository
import com.crazycoderz.zunokids.data.repository.AuthRepositoryImpl
import com.crazycoderz.zunokids.util.SoundManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideUserPreferencesRepo(
            @ApplicationContext context: Context
        ): UserPreferencesRepo = UserPreferencesRepo(context)

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

        @Provides
        @Singleton
        fun provideTtsManager(@ApplicationContext context: Context): TtsManager = TtsManager(context)

        @Provides
        @Singleton
        fun provideSoundManager(@ApplicationContext context: Context): SoundManager = SoundManager(context)
    }
}
