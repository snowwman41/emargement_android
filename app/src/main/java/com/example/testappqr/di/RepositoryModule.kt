package com.example.testappqr.di

import com.example.testappqr.data.repository.FakeProfessorRepositoryImpl
import com.example.testappqr.data.repository.FakeStudentRepositoryImpl
import com.example.testappqr.domain.repository.ProfessorRepository
import com.example.testappqr.domain.repository.StudentRepository
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
//    fun provideRepository(
//        apiService: ApiService
//
//        ): ProfessorRepository {
//
//        return ProfessorRepositoryImpl(apiService)
//    }
    @Provides
    @Singleton
    fun provideStudentRepository(
    ): StudentRepository {

        return FakeStudentRepositoryImpl()
    }
    @Provides
    @Singleton
    fun provideProfessorRepository(
    ): ProfessorRepository {

        return FakeProfessorRepositoryImpl()
    }
}
