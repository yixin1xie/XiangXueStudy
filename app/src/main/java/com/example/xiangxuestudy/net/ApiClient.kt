package com.example.xiangxue.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private object Holder{
        val INSTANCE = ApiClient()
    }
    //派生
    companion object{
        val instance = Holder.INSTANCE
    }

    fun<T> instanceRetrofit(apiInterface: Class<T>):T{
        val mOkHttpClient = OkHttpClient().newBuilder()
            .readTimeout(10000,TimeUnit.MILLISECONDS)
            .connectTimeout(10000,TimeUnit.MILLISECONDS)
            .writeTimeout(10000,TimeUnit.MILLISECONDS)
            .build()

        val retrofit: Retrofit=Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .client(mOkHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(apiInterface)
    }
}