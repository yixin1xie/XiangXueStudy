package com.example.xiangxue.entity

data class LoginResponWrapper<T>(val data:T,val errorCode:Int,val errorMsg:String)
