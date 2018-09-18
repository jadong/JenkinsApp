package com.dabaoqi.jenkinsapp.jobdetail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.jobdetail.helper.OnMoveAndSwipedListener
import com.dabaoqi.jenkinsapp.jobdetail.viewholder.FileItemHolder
import com.dabaoqi.jenkinsapp.jobdetail.viewholder.FooterViewHolder
import java.io.File
import java.util.*

/**
 *
 * Created by zengwendong on 2017/6/21.
 */
class DownloadDirAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnMoveAndSwipedListener {

    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2

    private val fileList = ArrayList<File>()
    private var hasMore = true

    var url: String? = null

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
    }

    fun setData(dataList: List<File>?) {
        if (dataList != null) {
            fileList.clear()
            addData(dataList)
        }
    }

    fun addData(dataList: List<File>?) {
        if (dataList != null) {
            fileList.addAll(dataList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view: View
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_file_item, parent, false)
            return FileItemHolder(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_footerview, parent, false)
        }

        return FooterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FileItemHolder) {
            val file = fileList[position]
            holder.initData(file,url)
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

        var count = fileList.size

        count++//底部view

        return count
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        //交换dataList数据的位置
        Collections.swap(fileList, fromPosition, toPosition)
        //交换RecyclerView列表中item的位置
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        val file = fileList[position]
        if (file.exists()) {
            file.delete()
            fileList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}