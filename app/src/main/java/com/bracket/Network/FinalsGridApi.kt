package com.bracket.Network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class FinalsGridApi{

    companion object {

        private var retrofit: Retrofit ?=null
        private const val Api_Url: String = ""

        fun getFinalsGrid(): Retrofit? {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(Api_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

}