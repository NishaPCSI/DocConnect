package com.example.docconnect

import ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFile {
    object RetrofitClient {
        private const val BASE_URL = "http://localhost:3000/"
//        private const val BASE_URL = "http://10.0.2.2:3000/"

        //        private const val BASE_URL = "http://localhost:3000/"
        val instance: AuthService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthService::class.java)
        }
        val apiInstance: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

        }
    }

}


