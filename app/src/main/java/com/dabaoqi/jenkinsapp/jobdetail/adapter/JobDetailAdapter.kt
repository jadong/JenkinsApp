package com.dabaoqi.jenkinsapp.jobdetail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.jobdetail.model.Task
import com.dabaoqi.jenkinsapp.jobdetail.viewholder.FooterViewHolder
import com.dabaoqi.jenkinsapp.jobdetail.viewholder.JobDetailItemHolder
import java.util.*

/**
 * Created by zengwendong on 2017/6/19.
 */
class JobDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    private val tasks = ArrayList<Task>()
    private var hasMore = true

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
    }

    fun setData(dataList: List<Task>?) {
        if (dataList != null) {
            tasks.clear()
            addData(dataList)
        }
    }

    fun addData(dataList: List<Task>?) {
        if (dataList != null) {
            tasks.addAll(dataList)
            notifyDataSetChanged()
        }
    }

    fun refreshFirstTask(task:Task){
        val firstTask = tasks[0]
        if (firstTask.num == task.num) {//是同一任务
            tasks.removeAt(0)
        }
        tasks.add(0,task)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_job_detail_item, parent, false)
            return JobDetailItemHolder(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_footerview, parent, false)
        }

        return FooterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JobDetailItemHolder) {
            val task = tasks[position]
            holder.initData(task)
        } else if (holder is FooterViewHolder) {
            val footerViewHolder = holder
            if (hasMore) {//是否有下一页数据
                footerViewHolder.setLoadingText()
            } else {
                footerViewHolder.setLoadEnd()//已经加载完成
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        if (position == itemCount - 1) {//底部view
            return TYPE_FOOTER
        }

        return TYPE_ITEM
    }

    override fun getItemCount(): Int {

        var count = tasks.size

        count++//底部view

        return count
    }
}
