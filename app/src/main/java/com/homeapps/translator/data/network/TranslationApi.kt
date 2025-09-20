package com.homeapps.translator.data.network

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import com.homeapps.translator.BuildConfig
import com.homeapps.translator.data.network.models.TranslationResponseDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TranslationApi {

    @GET("/api/public/v1/words/search")
    suspend fun requestTranslation(
        @Query("search") word: String
    ): Result<TranslationResponseDTO>
}

fun createTranslationApi(baseUrl: String): TranslationApi {
    val client = OkHttpClient.Builder()
        .addInterceptor(
            interceptor = HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.HEADERS)
        )
        .callTimeout(15_000L, TimeUnit.MILLISECONDS)
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(client)
        .build()
    return retrofit.create()
}