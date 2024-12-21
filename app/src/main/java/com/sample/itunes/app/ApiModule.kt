package com.sample.itunes.app

import com.sample.itunes.remote.ApiInterface
import com.sample.itunes.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ApiModule {


    @Provides
    @Singleton
    fun bindsApiDataSource(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun bindsAppRepo(apiDataSource: ApiInterface): AppRepository {
        return AppRepository(apiDataSource)
    }
}