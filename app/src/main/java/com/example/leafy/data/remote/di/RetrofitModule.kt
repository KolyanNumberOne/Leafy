package com.example.leafy.data.remote.di

import com.example.leafy.data.remote.api.GigaChatApiDataSource
import com.example.leafy.data.remote.api.GigaChatAuth
import com.example.leafy.data.remote.api.PlantApiDataSource
import com.example.leafy.data.remote.api.RemotePlantDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.security.cert.X509Certificate
import javax.inject.Qualifier
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun createUnsafeOkHttpClient(): OkHttpClient {
    val trustAllCertificates = object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
    }

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, arrayOf<TrustManager>(trustAllCertificates), java.security.SecureRandom())

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustAllCertificates)
        .hostnameVerifier { _, _ -> true }
        .build()
}

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val okHttpClient = createUnsafeOkHttpClient()

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PlantApi

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Host

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GigaChat

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthGigaChat

    @Provides
    @Singleton
    @Host
    fun providePlantRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://62.113.97.245:8080/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    @PlantApi
    fun provideRemotePlantRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://plant.id/api/v3/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    @GigaChat
    fun provideGigaChatApiDataSource(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gigachat.devices.sberbank.ru/api/v1/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @AuthGigaChat
    fun provideGigaChatAuthR(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://ngw.devices.sberbank.ru:9443/api/v2/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGigaChatAuth(@AuthGigaChat retrofit: Retrofit): GigaChatAuth =
        retrofit.create(GigaChatAuth::class.java)

    @Provides
    @Singleton
    fun provideGigaChatApi(@GigaChat retrofit: Retrofit): GigaChatApiDataSource =
        retrofit.create(GigaChatApiDataSource::class.java)

    @Provides
    @Singleton
    fun provideRemotePlantDataSource(@Host retrofit: Retrofit): RemotePlantDataSource =
        retrofit.create(RemotePlantDataSource::class.java)

    @Provides
    @Singleton
    fun providePlantApiDataSource(@PlantApi retrofit: Retrofit): PlantApiDataSource =
        retrofit.create(PlantApiDataSource::class.java)
}