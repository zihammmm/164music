package com.zihany.cloudmusic.main.mvvm.view

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivityRankBinding
import com.zihany.cloudmusic.main.adapter.RankAdapter
import com.zihany.cloudmusic.main.bean.TopListBean
import com.zihany.cloudmusic.main.mvvm.view.fragments.WowFragment
import com.zihany.cloudmusic.main.mvvm.viewmodel.WowViewModel

class RankActivity: BaseActivity<WowViewModel>() {
    companion object {
        const val TAG = "RankActivity"
    }

    private lateinit var adapter: RankAdapter
    private lateinit var binding: ActivityRankBinding
    private var list = ArrayList<TopListBean.ListBean>()

    private val listener = object : RankAdapter.OnTopListClickListener {
        override fun onClickTopList(position: Int) {
            if (list.isNotEmpty()) {
                val intent = Intent(this@RankActivity, PlayListActivity::class.java)
                val bean = list[position]
                intent.putExtra(WowFragment.PLAYLIST_NAME, bean.name)
                intent.putExtra(WowFragment.PLAYLIST_PICURL, bean.coverImgUrl)
                intent.putExtra(WowFragment.PLAYLIST_CREATOR_NICKNAME, "")
                intent.putExtra(WowFragment.PLAYLIST_CREATOR_AVATARURL, "")
                intent.putExtra(WowFragment.PLAYLIST_ID, bean.id)
                startActivity(intent)
            }
        }
    }

    override fun initData() {
        setBackBtn(getString(R.string.colorWhite))
        setLeftTitleText(getString(R.string.rank), getString(R.string.colorWhite))
        list.clear()
        adapter = RankAdapter(this)
        adapter.listener = listener
        binding.rvToplist.layoutManager = GridLayoutManager(this, 3)
        binding.rvToplist.adapter = adapter

    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivityRankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImmersionBar.with(this)
                .transparentBar()
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(false)
                .init()
    }
}