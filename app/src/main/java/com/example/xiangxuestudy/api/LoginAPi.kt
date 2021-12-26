package com.example.xiangxue.api

import com.example.xiangxue.entity.LoginResponWrapper
import com.example.xiangxue.entity.LoginResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginAPi {

    @POST("/user/login")
    @FormUrlEncoded
    fun loginAction(@Field("username")username:String,
    @Field("password") password:String)
    : Observable<LoginResponWrapper<LoginResponse>>
}