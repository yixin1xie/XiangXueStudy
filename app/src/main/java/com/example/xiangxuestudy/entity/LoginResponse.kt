package com.example.xiangxue.entity


/**
 * "data": {
"admin": false,
"chapterTops": [],
"coinCount": 10,
"collectIds": [],
"email": "",
"icon": "",
"id": 122610,
"nickname": "yixinxie",
"password": "",
"publicName": "yixinxie",
"token": "",
"type": 0,
"username": "yixinxie"
}
 */
data class LoginResponse(
    val admin:Boolean,
    val chapterTops:List<*>,
    val coinCount:Int,
    val collectIds:List<*>,
    val email:String?,
    val icon:String?,
    val id:String?,
    val nickname:String?,
    val password:String?,
    val publicName:String?,
    val token:String?,
    val type:Int,
    val username:String?
)
