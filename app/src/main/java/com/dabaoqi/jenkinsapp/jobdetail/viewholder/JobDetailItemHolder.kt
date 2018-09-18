package com.dabaoqi.jenkinsapp.jobdetail.viewholder

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.jobdetail.TaskDetailActivity
import com.dabaoqi.jenkinsapp.jobdetail.model.Task
import com.dabaoqi.jenkinsapp.util.AppUtils

/**
 * Created by zengwendong on 2017/6/19.
 */
class JobDetailItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var tv_task_num: TextView = itemView.findViewById(R.id.tv_task_num) as TextView
    private var tv_status: TextView = itemView.findViewById(R.id.tv_status) as TextView
    private var iv_status_icon: ImageView = itemView.findViewById(R.id.iv_status_icon) as ImageView
    private var task: Task? = null
    private var animSet: AnimatorSet? = null

    init {
        itemView.setOnClickListener {
            if (TextUtils.equals(task?.status, "SUCCESS")) {
                val intent = Intent(itemView.context, TaskDetailActivity::class.java)
                intent.putExtra("task", task)
                itemView.context.startActivity(intent)
            } else if (TextUtils.equals(task?.status, "BUILDING")) {
                AppUtils.showShortToast("正在打包中...，无法进入")
            } else {
                AppUtils.showShortToast("任务打包失败，无法进入")
            }
        }
    }

    fun initData(task: Task?) {
        if (task == null) {
            return
        }
        this.task = task
        tv_task_num.text = task.num
        tv_status.text = task.status
        if (TextUtils.equals(task.status, "BUILDING")) {
            iv_status_icon.setImageResource(R.drawable.ic_building)
            startAnim()
        } else if (TextUtils.equals(task.status, "SUCCESS")) {
            iv_status_icon.setImageResource(R.drawable.ic_success)
            stopAnim()
        } else if (TextUtils.equals(task.status, "FAILURE")) {
            iv_status_icon.setImageResource(R.drawable.ic_error)
            stopAnim()
        } else {
            iv_status_icon.setImageResource(R.drawable.ic_do_not)
            stopAnim()
        }

    }

    private fun JobDetailItemHolder.startAnim() {
        animSet = AnimatorSet()
        val scaleX = ObjectAnimator.ofFloat(iv_status_icon, "scaleX", 1f, 0.5f, 1f)
        scaleX.repeatCount = ObjectAnimator.INFINITE
        scaleX.repeatMode = ObjectAnimator.INFINITE
        val scaleY = ObjectAnimator.ofFloat(iv_status_icon, "scaleY", 1f, 0.5f, 1f)
        scaleY.repeatCount = ObjectAnimator.INFINITE
        scaleY.repeatMode = ObjectAnimator.INFINITE
        animSet!!.duration = 1000
        animSet!!.interpolator = LinearInterpolator()
        animSet!!.play(scaleX).with(scaleY)
        animSet!!.start()
    }

    private fun stopAnim() {
        animSet?.cancel()
    }

}
