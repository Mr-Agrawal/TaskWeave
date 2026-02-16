package com.example.taskweave.home.di

import com.example.taskweave.home.data.repository.ProjectRepositoryImpl
import com.example.taskweave.home.domain.repository.ProjectRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppHomeModule {

    @Singleton
    @Provides
    fun provideProjectRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): ProjectRepository {
        return ProjectRepositoryImpl(
            firebaseAuth = firebaseAuth,
            firebaseFirestore = firebaseFirestore
        )
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}