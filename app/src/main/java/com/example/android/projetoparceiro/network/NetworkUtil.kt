package com.example.android.projetoparceiro.network

import com.example.android.projetoparceiro.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.io.IOException

class NetworkUtil {
    fun getRetrofit(): RetrofitInterface {

        val rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io())

        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface::class.java)

    }

    fun getAuthenticatedRetrofit(token: String): RetrofitInterface {

        val client = OkHttpClient.Builder().addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                return chain.proceed(newRequest)
            }
        }).build()

        val rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io())

        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface::class.java)

    }
}