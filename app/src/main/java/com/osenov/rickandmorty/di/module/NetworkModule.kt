package com.osenov.rickandmorty.di.module

import com.google.gson.Gson
import com.osenov.rickandmorty.Config.CHARACTER_URL
import com.osenov.rickandmorty.data.remote.CharacterService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson) : GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideWeatherService(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): CharacterService {
        return Retrofit.Builder()
            .baseUrl(CHARACTER_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
            .create(CharacterService::class.java)
    }

}