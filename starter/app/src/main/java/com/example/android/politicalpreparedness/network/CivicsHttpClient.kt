package com.example.android.politicalpreparedness.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class CivicsHttpClient: OkHttpClient() {

    companion object {

        const val API_KEY = "" //TODO: Place your API Key Here

        fun getClient(): OkHttpClient {
            return Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val url = original
                                .url()
                                .newBuilder()
                                .addQueryParameter("key", API_KEY)
                                .build()
                        val request = original
                                .newBuilder()
                                .url(url)
                                .build()
                        chain.proceed(request)
                    }
                    .build()
        }

    }

}