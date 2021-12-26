package com.example.xiangxue.net

import android.util.Log
import com.example.xiangxue.entity.LoginResponWrapper
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class ApiResponse<T>: Observer<LoginResponWrapper<T>> {
    abstract fun success(data:T?)
    abstract fun failure(errorMsg:String)
    override fun onSubscribe(d: Disposable) {
        //订阅时的时间
    }

    override fun onNext(t: LoginResponWrapper<T>) {
        //发送事件处理
        if(t.data==null){
            failure(t.errorMsg);
        }else{
            success(t.data)
        }
    }

    override fun onError(e: Throwable) {
        //异常事件处理
    }

    override fun onComplete() {
        //时间结束处理
    }
}