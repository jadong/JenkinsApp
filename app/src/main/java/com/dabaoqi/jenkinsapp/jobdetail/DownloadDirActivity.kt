package com.dabaoqi.jenkinsapp.jobdetail

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.base.BaseActivity
import com.dabaoqi.jenkinsapp.jobdetail.adapter.DownloadDirAdapter
import com.dabaoqi.jenkinsapp.jobdetail.helper.ItemTouchHelperCallback
import com.dabaoqi.jenkinsapp.jobdetail.presenter.DownloadDirPresenter
import com.dabaoqi.jenkinsapp.jobdetail.view.IDownloadDirView
import com.dabaoqi.jenkinsapp.util.AppUtils
import kotlinx.android.synthetic.main.activity_download_dir.*
import java.io.File

/**
 * 下载文件目录展示
 *
 * Created by zengwendong on 2017/6/22.
 */
class DownloadDirActivity : BaseActivity(),IDownloadDirView {

    val downloadDirPresenter = DownloadDirPresenter(this)
    var downloadDirAdapter = DownloadDirAdapter()

    override fun getContentView(): Int {
        return R.layout.activity_download_dir
    }

    override fun initData() {
        recycler_view_dir.layoutManager = LinearLayoutManager(this)
        recycler_view_dir.adapter = downloadDirAdapter

        val dirFlag = intent.getStringExtra("dirFlag")
        val url = intent.getStringExtra("url")
        downloadDirAdapter.url = url

        //读取目录文件
        downloadDirPresenter.readDirFile(dirFlag)

        //关联ItemTouchHelper和RecyclerView
        val callback = ItemTouchHelperCallback(downloadDirAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recycler_view_dir)
    }

    override fun refreshDir(fileList: List<File>) {
        downloadDirAdapter.setHasMore(false)
        downloadDirAdapter.setData(fileList)
    }

    override fun readDirFailure() {
        AppUtils.showShortToast("未读取到目录文件")
    }
}
