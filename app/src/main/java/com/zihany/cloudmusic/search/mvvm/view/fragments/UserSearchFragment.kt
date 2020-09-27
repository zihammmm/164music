package com.zihany.cloudmusic.search.mvvm.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.FragmentRecyclerviewBinding
import com.zihany.cloudmusic.personal.mvvm.view.PersonalInfoActivity
import com.zihany.cloudmusic.search.adapter.UserSearchAdapter
import com.zihany.cloudmusic.search.bean.UserSearchBean
import com.zihany.cloudmusic.search.mvvm.viewmodel.SearchViewModel
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.ToastUtils

class UserSearchFragment constructor(
        private val type: String?
): BaseFragment<SearchViewModel>() {
    companion object {
        const val TAG = "UserSearchFragment"
    }

    private val searchType = 1002
    private var keywords: String? = null
    private var userList = ArrayList<UserSearchBean.ResultBean.UserprofilesBean>()
    private lateinit var adapter: UserSearchAdapter
    private var needRefresh = false
    private var userBean: UserSearchBean? = null

    private lateinit var binding: FragmentRecyclerviewBinding

    private val listener = object : UserSearchAdapter.OnUserClickListener {
        override fun onUserClick(position: Int, type: Int) {
            if (type == UserSearchAdapter.OnUserClickListener.USER_CHECK) {
                userBean?.let {
                    val bean = it.result!!.userprofiles!![position]
                    val intent = Intent(activity, PersonalInfoActivity::class.java)
                    intent.putExtra(PersonalInfoActivity.USER_BEAN, GsonUtil.toJson(bean))
                    startActivity(intent)
                }
            } else {
                ToastUtils.show("关注 : $position")
            }
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initVariables(bundle: Bundle) {

    }

    override fun initData() {
        adapter = UserSearchAdapter(context!!)
        adapter.keywords = keywords
        adapter.listener = listener
        binding.rv.layoutManager = LinearLayoutManager(context)
        binding.rv.adapter = adapter

        keywords?.let{
            showDialog()
        }
    }

    override fun onVisible() {
        super.onVisible()
        if (needRefresh) {
            needRefresh = false
            showDialog()
        }
    }
}