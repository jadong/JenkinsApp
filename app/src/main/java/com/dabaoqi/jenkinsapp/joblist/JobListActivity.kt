package com.dabaoqi.jenkinsapp.joblist

import android.content.Context
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.dabaoqi.jenkinsapp.R
import com.dabaoqi.jenkinsapp.base.BaseActivity
import com.dabaoqi.jenkinsapp.joblist.model.JobEntity
import com.dabaoqi.jenkinsapp.joblist.model.JobViewsDataResult
import com.dabaoqi.jenkinsapp.joblist.presenter.JobViewsPresenter
import com.dabaoqi.jenkinsapp.joblist.view.IJobViewsView
import kotlinx.android.synthetic.main.activity_main.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


class JobListActivity : BaseActivity(), IJobViewsView {

    val presenter: JobViewsPresenter = JobViewsPresenter(this)
    val fragmentMap = mutableMapOf<String, JobListFragment>()

    override fun initData() {
        presenter.getJobList()//请求job列表接口
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun onLoadSuccess(dataResult: JobViewsDataResult) {
        showViews(dataResult)
        loadingView.visibility = View.GONE
    }

    override fun onLoadFailure() {
        Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
        loadingView.visibility = View.GONE
    }

    private fun showViews(dataResult: JobViewsDataResult) {
        val jobViews = dataResult.jobViews
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return jobViews.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.parseColor("#333333")
                colorTransitionPagerTitleView.selectedColor = Color.parseColor("#ffa500")
                colorTransitionPagerTitleView.text = jobViews[index].name
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
                colorTransitionPagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.setColors(Color.parseColor("#ffa500"))
                return indicator
            }
        }

        magicIndicator.navigator = commonNavigator

        viewPager.adapter = object : FragmentPagerAdapter(this@JobListActivity.supportFragmentManager) {
            override fun getItem(position: Int): JobListFragment? {
                var currentTitle = jobViews[position].name

                return if (fragmentMap.containsKey(currentTitle)) {
                    fragmentMap[currentTitle]
                } else {

                    var jobs: List<JobEntity> = if (TextUtils.equals(dataResult.primaryView.name, currentTitle)) {
                        dataResult.jobs
                    } else {
                        mutableListOf()
                    }

                    val fragment = JobListFragment()
                    fragment.initData(jobViews[position], jobs)
                    fragmentMap[currentTitle] = fragment
                    fragment
                }

            }

            override fun getCount(): Int {
                return jobViews.size
            }

        }

        ViewPagerHelper.bind(magicIndicator, viewPager)
        val fragmentContainerHelper = FragmentContainerHelper(magicIndicator)
        fragmentContainerHelper.handlePageSelected(0)
    }
}
