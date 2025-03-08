package com.example.testappqr.di

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.data.repository.FakeProfessorRepositoryImpl
import com.example.testappqr.data.repository.ProfessorRepositoryImpl
import com.example.testappqr.domain.repository.ProfessorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
//    @Provides
//    @Singleton
//    fun provideBookRepository(
//        apiService: ApiService
//
//        ): ProfessorRepository {
//
//        return ProfessorRepositoryImpl(apiService)
//    }
    @Provides
    @Singleton
    fun provideBookRepository(
    ): ProfessorRepository {

        return FakeProfessorRepositoryImpl()
    }
}
