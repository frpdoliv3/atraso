package com.fpoliveira.atraso.di

import android.content.Context
import com.fpoliveira.atraso.feat_route_details.data.remote.ScheduleApi
import com.fpoliveira.atraso.feat_route_details.data.remote.StationDetailsApi
import com.fpoliveira.atraso.feat_route_details.data.repository.ScheduleRepositoryImpl
import com.fpoliveira.atraso.feat_route_details.domain.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideScheduleApi(): ScheduleApi = Retrofit.Builder()
        .baseUrl("https://www.infraestruturasdeportugal.pt/negocios-e-servicos/horarios-ncombio/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    @Provides
    @Singleton
    fun provideStationDetailsApi(): StationDetailsApi = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/api/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    @Provides
    @Singleton
    fun provideScheduleRepository(
        @ApplicationContext context: Context,
        api: ScheduleApi
    ): ScheduleRepository = ScheduleRepositoryImpl(context, api)
}