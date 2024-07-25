package br.com.usinasantafe.pcpcomp.external.webservices

import android.content.Context
import br.com.usinasantafe.pcpcomp.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

fun provideRetrofit(appContext: Context): Retrofit = Retrofit.Builder()
        .baseUrl(appContext.getString(R.string.base_url))
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpClient())
        .build()

