package com.zihany.cloudmusic.dj.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivityRadioBinding
import com.zihany.cloudmusic.dj.bean.DjPayGiftBean
import com.zihany.cloudmusic.dj.bean.DjRecommandBean
import com.zihany.cloudmusic.dj.mvvm.viewmodel.DjViewModel
import com.zihany.cloudmusic.main.adapter.PlayListAdapter
import com.zihany.cloudmusic.main.bean.PlaylistBean
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.ToastUtils

class RadioRecommendActivity: BaseActivity() {
    companion object {
        const val TAG = "RadioRecommendActivity"
    }
    private lateinit var binding: ActivityRadioBinding
    private lateinit var djRecAdapter: PlayListAdapter
    private lateinit var djPayGiftAdapter: PlayListAdapter
    private lateinit var coverAdapter: PlayListAdapter
    private lateinit var voiceBookAdapter: PlayListAdapter
    private lateinit var motionAdapter: PlayListAdapter
    private lateinit var broadcastAdapter: PlayListAdapter
    private lateinit var musicStoryAdapter: PlayListAdapter
    private lateinit var entertainmentAdapter: PlayListAdapter
    private lateinit var beautifulAdapter: PlayListAdapter
    private lateinit var animAdapter: PlayListAdapter
    private lateinit var showAdapter: PlayListAdapter
    private lateinit var electricAdapter: PlayListAdapter
    private val djRecRadios = ArrayList<DjRecommandBean.DjRadiosBean>()
    private val recList = ArrayList<PlaylistBean>()
    private val payGiftList = ArrayList<PlaylistBean>()
    private var recIndex = 0

    private val recListener = object : PlayListAdapter.OnPlayListClickListener {
        override fun onClickListener(position: Int) {
            var djRadiosBean: DjRecommandBean.DjRadiosBean = DjRecommandBean.DjRadiosBean()
            for (bean: DjRecommandBean.DjRadiosBean in djRecRadios) {
                if (recList[position].playlistName == bean.name) {
                    djRadiosBean = bean
                }
            }
            val intent = Intent(this@RadioRecommendActivity, RadioActivity::class.java)
            intent.putExtra(RadioActivity.SUB_COUNT, djRadiosBean.subCount)
            intent.putExtra(RadioActivity.RADIO_NAME, djRadiosBean.name)
            intent.putExtra(RadioActivity.RID, djRadiosBean.id)
            intent.putExtra(RadioActivity.IS_SUB, false)
            intent.putExtra(RadioActivity.COVER_URL, djRadiosBean.picUrl)
            startActivity(intent)
        }
    }

    private val payGiftListener = object : PlayListAdapter.OnPlayListClickListener {
        override fun onClickListener(position: Int) {
            LogUtil.d(TAG, "我没有钱")
        }
    }

    override fun initData() {
        setBackBtn(getString(R.string.colorWhite))
        setLeftTitleText("电台", "#ffffff")

        djRecAdapter = PlayListAdapter(this)
        val recManager = GridLayoutManager(this, 3)
        binding.rvRecommendDj.layoutManager = recManager
        binding.rvRecommendDj.adapter = djRecAdapter
        djRecAdapter.listener = recListener

        djPayGiftAdapter = PlayListAdapter(this)
        val payGiftManager = GridLayoutManager(this, 3)
        binding.rvPaygiftDj.layoutManager = payGiftManager
        binding.rvPaygiftDj.adapter = djPayGiftAdapter
        djPayGiftAdapter.listener = payGiftListener

        coverAdapter = PlayListAdapter(this)
        binding.rvCoverDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvCoverDj.adapter = coverAdapter

        voiceBookAdapter = PlayListAdapter(this)
        binding.rvVoiceBookDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvVoiceBookDj.adapter = voiceBookAdapter

        motionAdapter = PlayListAdapter(this)
        binding.rvMotionDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvMotionDj.adapter = motionAdapter

        broadcastAdapter = PlayListAdapter(this)
        binding.rvBroadcastDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvBroadcastDj.adapter = broadcastAdapter

        musicStoryAdapter = PlayListAdapter(this)
        binding.rvMusicStoryDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvMusicStoryDj.adapter = musicStoryAdapter

        entertainmentAdapter = PlayListAdapter(this)
        binding.rvEntertainmentDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvEntertainmentDj.adapter = entertainmentAdapter

        beautifulAdapter = PlayListAdapter(this)
        binding.rvBeautifulDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvBeautifulDj.adapter = beautifulAdapter

        animAdapter = PlayListAdapter(this)
        binding.rvAnimDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvAnimDj.adapter = animAdapter

        showAdapter = PlayListAdapter(this)
        binding.rvShowDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvShowDj.adapter = showAdapter

        electricAdapter = PlayListAdapter(this)
        binding.rvEntertainmentDj.layoutManager = GridLayoutManager(this, 3)
        binding.rvEntertainmentDj.adapter = electricAdapter

//        showDialog()
//        viewModel.getDjRecommend()
//        viewModel.getDjPayGift(3, 0)
//        viewModel.getDjCategoryRecommend()
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun startObserve() {
        TODO("Not yet implemented")
    }

    override fun onClick(view: View) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel = ViewModelProvider(this)[DjViewModel::class.java]
//        viewModel.apply {
//            viewModel.djPayGiftBean.observe(this@RadioRecommendActivity, Observer<DjPayGiftBean> {
//                onGetDjRecommendSuccess(it)
//            })
//        }
    }

    inner class RadioRecommendPresenter {
        private val intent = Intent()
        fun onClickRlDjRank(v: View?) {
            ToastUtils.show("这版不做")
        }

        fun onClickTvDjPlayground(v: View?) {
            replaceRecDj()
        }

        fun onClickRlDjCat(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioCatActivity::class.java)
            startActivity(intent)
        }

        fun onClickRlDjHq(v: View?) {
            onClickTvDjPaygift(v)
        }

        fun onClickTvDjPaygift(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 0)
            intent.putExtra(RadioListActivity.TITLE_NAME, "付费精选")
            startActivity(intent)
        }

        fun onClickTvCover(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 2001)
            intent.putExtra(RadioListActivity.TITLE_NAME, "创作|翻唱")
            startActivity(intent)
        }

        fun onClickTvVoiceBook(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 10001)
            intent.putExtra(RadioListActivity.TITLE_NAME, "有声书")
            startActivity(intent)
        }

        fun onClickTvMotion(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 3)
            intent.putExtra(RadioListActivity.TITLE_NAME, "情感频道")
            startActivity(intent)
        }

        fun onClickTvBroadcast(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 7)
            intent.putExtra(RadioListActivity.TITLE_NAME, "广播剧")
            startActivity(intent)
        }

        fun onClickTvMusicStory(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 2)
            intent.putExtra(RadioListActivity.TITLE_NAME, "音乐故事")
            startActivity(intent)
        }

        fun onClickTvEntertainment(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 4)
            intent.putExtra(RadioListActivity.TITLE_NAME, "娱乐|影视")
            startActivity(intent)
        }

        fun onClickTv3d(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 10002)
            intent.putExtra(RadioListActivity.TITLE_NAME, "3D|电子")
            startActivity(intent)
        }

        fun onClickTvBeautiful(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 6)
            intent.putExtra(RadioListActivity.TITLE_NAME, "美文读物")
            startActivity(intent)
        }

        fun onClickTvAnim(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 3001)
            intent.putExtra(RadioListActivity.TITLE_NAME, "二次元")
            startActivity(intent)
        }

        fun onClickTvShow(v: View?) {
            intent.setClass(this@RadioRecommendActivity, RadioListActivity::class.java)
            intent.putExtra(RadioListActivity.TYPE, 5)
            intent.putExtra(RadioListActivity.TITLE_NAME, "脱口秀")
            startActivity(intent)
        }

        private fun replaceRecDj() {
            var i: Int
            recList.clear()
            for (count in 2 downTo 0) {
                val playlistBean = PlaylistBean()
                i = if (recIndex > djRecRadios.size - 1) {
                    recIndex % djRecRadios.size
                }else {
                    recIndex
                }
                playlistBean.playlistName = djRecRadios[i].name.toString()
                playlistBean.playlistCoverUrl = djRecRadios[i].picUrl.toString()
                recList.add(playlistBean)
                recIndex++
            }
            djRecAdapter.notifyDataSetChanged(recList)
        }
    }

    fun onGetDjRecommendSuccess(bean: DjPayGiftBean) {
        hideDialog()
        payGiftList.clear()
        for (i: DjPayGiftBean.DataBean.ListBean in bean.data?.list!!) {
            val playlistBean = PlaylistBean()
            playlistBean.playlistName = i.name.toString()
            playlistBean.playlistCoverUrl = i.picUrl.toString()
            payGiftList.add(playlistBean)
        }
        djPayGiftAdapter.notifyDataSetChanged(payGiftList)
    }

    fun onGetDjRecommendFail(msg: String) {
        hideDialog()
    }
}