package br.com.usinasantafe.pcpcomp.external.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun provideHttpClient(): OkHttpClient {

    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(logging)
        .build()
}

fun provideRetrofitAndroidTest(baseTestApi: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseTestApi)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpClient())
        .build()



