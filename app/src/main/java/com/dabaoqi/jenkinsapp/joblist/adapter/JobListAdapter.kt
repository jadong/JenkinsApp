package com.dabaoqi.jenkinsapp.jobdetail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.jobdetail.viewholder.FooterViewHolder
import com.dabaoqi.jenkinsapp.joblist.model.JobEntity
import com.dabaoqi.jenkinsapp.joblist.viewholder.JobListItemHolder

import java.util.ArrayList

class JobListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    private val mJobInfos = ArrayList<JobEntity>()
    private var hasMore = true

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
    }

    fun setData(dataList: List<JobEntity>) {
        if (dataList != null) {
            mJobInfos.clear()
            addData(dataList)
        }
    }

    fun addData(dataList: List<JobEntity>) {
        if (dataList != null) {
            mJobInfos.addAll(dataList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View
        return if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_job_list_item, parent, false)
            JobListItemHolder(view)
        }
        else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_footerview, parent, false)
            FooterViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is JobListItemHolder) {
            val jonInfo = mJobInfos[position]
            holder.initData(jonInfo)
        }
//        else if (holder is FooterViewHolder) {
//            val footerViewHolder = holder
//            if (hasMore) {//是否有下一页数据
//                footerViewHolder.setLoadingText()
//            } else {
//                footerViewHolder.setLoadEnd()//已经加载完成
//            }
//        }
    }

    override fun getItemViewType(position: Int): Int {

//        if (position == itemCount - 1) {//底部view
//            return TYPE_FOOTER
//        }

        return TYPE_ITEM
    }

    override fun getItemCount(): Int {

        var count = mJobInfos.size

//        count++//底部view

        return count
    }
}
