package com.itmem.tot_hack.di

import com.itmem.tot_hack.core.BASE_URL
import com.itmem.tot_hack.core.log
import com.itmem.tot_hack.data.CurrentUserProviderImpl
import com.itmem.tot_hack.data.EmotionRepositoryImpl
import com.itmem.tot_hack.data.MessagesRepositoryImpl
import com.itmem.tot_hack.data.api.EmotionAPI
import com.itmem.tot_hack.data.api.FakeEmotionAPI
import com.itmem.tot_hack.domain.repository.CurrentUserProvider
import com.itmem.tot_hack.domain.repository.EmotionRepository
import com.itmem.tot_hack.domain.repository.MessagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideCurrentActivityProvider(
        impl: MessagesRepositoryImpl
    ): MessagesRepository = impl

    @Provides
    fun provideOkHttp() = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor(::log).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    @Provides
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): EmotionAPI = retrofit.create(EmotionAPI::class.java)

//    @Provides
//    fun provideApiService(api: FakeEmotionAPI): EmotionAPI = api

    @Provides
    fun provideCurrentUserProvider(provider: CurrentUserProviderImpl): CurrentUserProvider = provider

    @Provides
    fun provideEmotionRepository(repo: EmotionRepositoryImpl): EmotionRepository = repo
}