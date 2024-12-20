package com.example.leafy.data.remote.di

import com.example.leafy.data.remote.api.OpenAIApiDataSource
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
    // Создаем кастомный TrustManager, который игнорирует все сертификаты
    val trustAllCertificates = object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf() // Возвращаем пустой массив
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {}
    }

    // Инициализируем SSLContext с кастомным TrustManager
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, arrayOf<TrustManager>(trustAllCertificates), java.security.SecureRandom())

    // Создаем OkHttpClient с кастомным SSLContext
    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustAllCertificates)
        .hostnameVerifier { _, _ -> true } // Игнорируем проверку имени хоста
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
    annotation class OpenAI

    @Provides
    @Singleton
    @Host
    fun providePlantRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.99.44:8080/")
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
    @OpenAI
    fun provideOpenAIApiDataSource(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gigachat.devices.sberbank.ru/api/v1/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenAIApi(@OpenAI retrofit: Retrofit): OpenAIApiDataSource =
        retrofit.create(OpenAIApiDataSource::class.java)

    @Provides
    @Singleton
    fun provideRemotePlantDataSource(@Host retrofit: Retrofit): RemotePlantDataSource =
        retrofit.create(RemotePlantDataSource::class.java)

    @Provides
    @Singleton
    fun providePlantApiDataSource(@PlantApi retrofit: Retrofit): PlantApiDataSource =
        retrofit.create(PlantApiDataSource::class.java)
}