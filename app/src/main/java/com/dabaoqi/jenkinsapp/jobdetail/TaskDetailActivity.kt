package com.dabaoqi.jenkinsapp.jobdetail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import com.bumptech.glide.Glide
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.base.BaseActivity
import com.dabaoqi.jenkinsapp.jobdetail.model.Task
import com.dabaoqi.jenkinsapp.jobdetail.presenter.TaskPresenter
import com.dabaoqi.jenkinsapp.jobdetail.view.ITaskDetailView
import com.dabaoqi.jenkinsapp.util.AppUtils
import kotlinx.android.synthetic.main.activity_task_detail.*


/**
 * 工程任务详情页
 * Created by zengwendong on 2017/6/20.
 */
class TaskDetailActivity : BaseActivity(), ITaskDetailView {

    var mappingUrl: String? = null
    var apkUrl: String? = null
    val taskPresenter: TaskPresenter = TaskPresenter(this)

    override fun getContentView(): Int {
        return R.layout.activity_task_detail
    }

    override fun initData() {

        val task:Task = intent.getSerializableExtra("task") as Task

        val num = task.num
        apkUrl = task.apkUrl.trim()
        mappingUrl = task.mappingUrl.trim()

        setTitle("#$num 打包结果")

        val codeUrl:String = "https://www.kuaizhan.com/common/encode-png?large=true&data=$apkUrl"
        Glide.with(this).load(codeUrl).into(imageView)

        val sb = StringBuilder()
        var index = 1
        task.changeSet.forEach {
            sb.append("$index.$it \n")
            index++
        }
        tv_modify_record.text = sb.toString()

        setListener()
    }


    private fun setListener() {

        //下载安装包
        btn_download_apk.setOnClickListener {
            taskPresenter.downloadApk(apkUrl!!)
        }

        //下载mapping文件
        btn_download_mapping.setOnClickListener {
            taskPresenter.downloadMapping(mappingUrl!!)
        }

        btn_apk_dir.setOnClickListener {
            val intent = Intent(this@TaskDetailActivity,DownloadDirActivity::class.java)
            intent.putExtra("dirFlag","apk")
            intent.putExtra("url",apkUrl)
            startActivity(intent)
        }

        btn_mapping_dir.setOnClickListener {
            val intent = Intent(this@TaskDetailActivity,DownloadDirActivity::class.java)
            intent.putExtra("dirFlag","mapping")
            intent.putExtra("url",mappingUrl)
            startActivity(intent)
        }
    }

    override fun downloadApkSuccess(filePath: String) {

        AlertDialog.Builder(this).setMessage("下载成功，是否继续安装APK").setPositiveButton("是", {
            dialog, _ ->
            AppUtils.installApk(this@TaskDetailActivity, filePath)

            dialog.dismiss()
        }).setNegativeButton("否", {
            dialog, _ ->
            dialog.dismiss()
        }).create().show()

    }

    override fun downloadMappingSuccess(filePath: String) {
        AppUtils.showLongToast("下载成功：$filePath")
    }

    override fun downloadMappingFailure() {
        AppUtils.showShortToast("下载mapping文件失败")
    }

    override fun downloadApkFailure() {
        AppUtils.showShortToast("下载安装包失败")
    }

    override fun getContext(): Context {
        return this
    }

}
