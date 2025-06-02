package com.example.testappqr.di

import com.example.testappqr.data.datasource.remote.ApiService
import com.example.testappqr.data.repository.FakeLoginRepositoryImpl
import com.example.testappqr.data.repository.LoginRepositoryImp
import com.example.testappqr.data.repository.ProfessorRepositoryImpl
import com.example.testappqr.data.repository.StudentRepositoryImp
import com.example.testappqr.domain.repository.LoginRepository
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
    @Provides
    @Singleton
    fun provideRepository(
        apiService: ApiService

    ): ProfessorRepository {

        return ProfessorRepositoryImpl(apiService)
    }

//    @Provides
//    @Singleton
//    fun provideLoginRepository(
//        apiService: ApiService
//    ): LoginRepository {
//        return LoginRepositoryImp(apiService)
//    }

    @Provides
    @Singleton
    fun provideStudentRepository(
        apiService: ApiService
    ): StudentRepository {

        return StudentRepositoryImp(apiService)
    }
    @Provides
    @Singleton
    fun provideLoginRepository(
    ): LoginRepository {
        return LoginRepositoryImp(
            apiService = TODO()
        )
    }
//    @Provides
//    @Singleton
//    fun provideStudentRepository(
//    ): StudentRepository {
//
//        return FakeStudentRepositoryImpl()
//    }
//    @Provides
//    @Singleton
//    fun provideProfessorRepository(
//    ): ProfessorRepository {
//
//        return FakeProfessorRepositoryImpl()
//    }
}
