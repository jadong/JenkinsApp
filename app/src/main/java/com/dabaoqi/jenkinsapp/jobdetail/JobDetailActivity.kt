package com.dabaoqi.jenkinsapp.jobdetail

import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.animation.LinearInterpolator
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.base.BaseActivity
import com.dabaoqi.jenkinsapp.jobdetail.adapter.JobDetailAdapter
import com.dabaoqi.jenkinsapp.jobdetail.model.BuildDesc
import com.dabaoqi.jenkinsapp.jobdetail.model.Task
import com.dabaoqi.jenkinsapp.jobdetail.presenter.JobDetailPresenter
import com.dabaoqi.jenkinsapp.jobdetail.view.IJobDetailView
import com.dabaoqi.jenkinsapp.util.AppUtils
import kotlinx.android.synthetic.main.activity_job_detail.*


/**
 *
 *  工程详情页
 *
 * Created by zengwendong on 2017/6/19.
 */
class JobDetailActivity : BaseActivity(), IJobDetailView {

    private val jobDetailPresenter: JobDetailPresenter = JobDetailPresenter(this)
    private val jobDetailAdapter: JobDetailAdapter = JobDetailAdapter()
    var name: String? = "" //job名称
    var isStartBuild: Boolean = false //是否已开始build
    var startBuildAnim: ObjectAnimator? = null
    var isBuildSuccess: Boolean = false
    private var repeat_time: Long = 5000 //每次请求间隔时间(毫秒）

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    override fun getContentView(): Int {
        return R.layout.activity_job_detail
    }

    override fun initData() {
        name = intent.getStringExtra("name")
        setTitle(name!!)
        jobDetailPresenter.getJobDetail(name!!)

        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recyclerView.adapter = jobDetailAdapter

        setListener()
    }

    private fun setListener() {

        //开始构建
        fab_start_build.setOnClickListener {
            if (!isStartBuild) {
                isStartBuild = true
                jobDetailPresenter.startBuild(name!!)

                AppUtils.showShortToast("开始构建...")

                //执行动画
                fab_start_build.setImageResource(R.drawable.ic_sync)
                startBuildAnim = ObjectAnimator.ofFloat(fab_start_build, "rotation", 0.0f, 359.0f)
                startBuildAnim!!.repeatCount = -1
                startBuildAnim!!.interpolator = LinearInterpolator()
                startBuildAnim!!.duration = 1000
                startBuildAnim!!.start()
            }
        }
    }

    override fun notifyDataChange(name: String, tasks: List<Task>) {
        setTitle(name)
        jobDetailAdapter.setHasMore(false)
        jobDetailAdapter.setData(tasks)
    }

    override fun onLoadFailure() {
        AppUtils.showShortToast("error")
    }

    /**
     * 启动build成功
     */
    override fun startBuildSuccess(buildDesc: BuildDesc) {

        repeat_time = buildDesc.repeat_time
        jobDetailAdapter.refreshFirstTask(buildDesc.task)

        postDelayRequest(buildDesc.task.num)
    }

    override fun startBuildFailure() {
        isBuildSuccess = false
        resetBuildStatus()
        AppUtils.showShortToast("启动失败")
    }

    override fun refreshBuildDetail(task: Task) {
        if (TextUtils.equals(task.status, "SUCCESS")) {
            isBuildSuccess = true //build成功
            resetBuildStatus()
        } else if (TextUtils.equals(task.status, "BUILDING")) {
            postDelayRequest(task.num)
        } else {
            resetBuildStatus()
        }
        jobDetailAdapter.refreshFirstTask(task)
    }

    private fun resetBuildStatus() {
        startBuildAnim?.cancel()
        isStartBuild = false //设为false，可以开启下次build
        fab_start_build.setImageResource(R.drawable.ic_play)
    }

    private fun postDelayRequest(num: String) {
        handler.postDelayed({
            jobDetailPresenter.getBuildDetail(name!!, num)
        }, repeat_time)
    }
}