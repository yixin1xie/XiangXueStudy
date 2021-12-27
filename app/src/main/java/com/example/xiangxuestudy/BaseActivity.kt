package com.example.xiangxuestudy

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
Date: 2021/12/27 16:02
author: leo
Description:
 */
abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {
    lateinit var  binding:V
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        Log.d("yixinxin","enter the create")
        super.onCreate(savedInstanceState, persistentState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
    }
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initBinding()

    protected abstract fun initViewModel()

    protected abstract fun loadData()
}