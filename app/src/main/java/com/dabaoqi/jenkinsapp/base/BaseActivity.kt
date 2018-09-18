package com.dabaoqi.jenkinsapp.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.dabaoqi.jenkinsapp.R

open abstract class BaseActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setNavigationOnClickListener {
            finish()
        }

        initData()

    }

    /**
     * 设置标题名称
     */
    fun setTitle(title: String) {
        toolbar?.title = title
    }

    abstract fun getContentView(): Int

    abstract fun initData()
}
