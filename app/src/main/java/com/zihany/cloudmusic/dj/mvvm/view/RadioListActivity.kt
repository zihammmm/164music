package com.zihany.cloudmusic.dj.mvvm.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.dj.adapter.RadioListAdapter
import com.zihany.cloudmusic.dj.mvvm.viewmodel.DjViewModel

class RadioListActivity: BaseActivity<DjViewModel>() {
    companion object {
        const val TAG = "RadioListActivity"
        const val TITLE_NAME = "titleName"
        const val TYPE = "type"
    }

    private lateinit var adapter: RadioListAdapter
    //TODO : xxx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DjViewModel::class.java]
    }

    override fun initData() {
        val intent = intent
        intent?.let {
            val titleName = intent.getStringExtra(TITLE_NAME)
            setLeftTitleText(titleName, "#ffffff")
            setBackBtn("#ffffff")


        }
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}