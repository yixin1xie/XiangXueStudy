package com.example.xiangxuestudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.xiangxue.api.LoginAPi
import com.example.xiangxue.entity.LoginResponse
import com.example.xiangxue.net.ApiClient
import com.example.xiangxue.net.ApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var hello: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello=findViewById(R.id.hello)
        hello.setOnClickListener{
            val userName="yixinxie";
            val password="12345678";
            ApiClient.instance.instanceRetrofit(LoginAPi::class.java)
                .loginAction(userName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ApiResponse<LoginResponse>(){
                    override fun success(data: LoginResponse?) {

                        Toast.makeText(this@MainActivity,"success ${data?.nickname} ${data?.id}", Toast.LENGTH_SHORT).show()
                    }

                    override fun failure(errorMsg: String) {
                        Toast.makeText(this@MainActivity,errorMsg, Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }
}