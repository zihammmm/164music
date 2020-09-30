package com.zihany.cloudmusic.main.mvvm.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentFeedBinding
import com.zihany.cloudmusic.main.bean.MainEventBean
import com.zihany.cloudmusic.main.mvvm.viewmodel.CloudVillageViewModel
import com.zihany.cloudmusic.personal.adapter.UserEventAdapter
import com.zihany.cloudmusic.personal.bean.UserEventBean
import com.zihany.cloudmusic.personal.mvvm.view.PersonalInfoActivity
import com.zihany.cloudmusic.search.bean.UserSearchBean
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.LogUtil

class CloudVillageFragment: BaseFragment<CloudVillageViewModel>() {
    companion object {
        const val TAG = "CloudVillageFragment"
    }

    private lateinit var binding: FragmentFeedBinding
    private lateinit var adapter: UserEventAdapter
    private var eventList: ArrayList<UserEventBean.EventsBean>? = null
    private val listener = object : UserEventAdapter.OnEventClickListener {
        override fun onAvatarClick(position: Int) {
            val userSearchBean = UserSearchBean.ResultBean.UserprofilesBean()
            val user = eventList?.get(position)?.user!!
            userSearchBean.userId = user.userId
            userSearchBean.avatarUrl = user.avatarUrl
            userSearchBean.backgroundUrl = user.backgroundUrl
            userSearchBean.nickname = user.nickname
            val intent = Intent(context, PersonalInfoActivity::class.java)
            val userSearchBeanString = GsonUtil.toJson(userSearchBean)
            intent.putExtra(PersonalInfoActivity.USER_BEAN, userSearchBeanString)
            startActivity(intent)
        }

        override fun onRelayClick(position: Int) {

        }

        override fun onCommentClick(position: Int) {

        }

        override fun onLikeClick(position: Int) {

        }

    }
    init {
        fragmentTitle = "云村"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CloudVillageViewModel::class.java]
        viewModel.apply {
            bean.observe(this@CloudVillageFragment, Observer<MainEventBean> {
                onGetMainEventSuccess(it)
            })

            getMainEventError.observe(this@CloudVillageFragment, Observer<String> {
                onGetMainEventFail(it)
            })
        }
    }

    private fun onGetMainEventSuccess(bean: MainEventBean) {
        hideDialog()
        LogUtil.d(TAG, "onGetMainEventSuccess: $bean")
        eventList = bean.event
        eventList?.let {
            adapter.notifyDataSetChanged(it)
        }
    }

    private fun onGetMainEventFail(msg: String) {
        LogUtil.d(TAG, "onGetMainEventFail: $msg")
    }

    override fun initData() {
        binding.rvEvent.layoutManager = LinearLayoutManager(context)
        adapter = UserEventAdapter(context!!)
        binding.rvEvent.adapter = adapter
        adapter.listener = listener

        showDialog()

    }

}