package com.wewrite.android.api

import com.wewrite.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIFactory {
    companion object {
        private const val server_url = BuildConfig.server_url
        private var INSTANCE:Retrofit? = null

        fun getInstance():Retrofit {
            if (INSTANCE == null) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(server_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }
    }
}