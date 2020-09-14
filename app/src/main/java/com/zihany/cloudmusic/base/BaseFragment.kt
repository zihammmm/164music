package com.zihany.cloudmusic.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zihany.cloudmusic.widget.LoadingDialog

abstract class BaseFragment : Fragment(), View.OnClickListener {
    companion object {
        const val TAG = "BaseFragment"
        const val SONG_URL = "http://music.163.com/song/media/outer/url?id="
    }

    protected var diaLog: LoadingDialog? = null
    protected var activity: Activity? = null
    var fragmentTitle: String? = null
    private var isFragmentVisible = false
    private var isFirstLoad = true
    private var forceLoad = false
    private var isPrepared = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDialog()
        val bundle = arguments
        if (bundle != null && bundle.size() > 0) {
            initVariables(bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isFirstLoad = true
        isPrepared = true
        val view = initView(inflater, container, savedInstanceState)
        lazyLoad()
        return view
    }

    protected abstract fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    protected abstract fun initVariables(bundle: Bundle)

    protected abstract fun initData()

    protected fun lazyLoad() {
        if (isPrepared && isFragmentVisible) {
            if (forceLoad || isFirstLoad) {
                isFirstLoad = false
                forceLoad = false
                initData()
            }
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    private fun createDialog() {
        if (diaLog == null) {
            diaLog = LoadingDialog(context!!, "Loading...")
        }
    }

    fun setForceLoad(forceLoad: Boolean) {
        this.forceLoad = forceLoad
    }

    fun isPrepared(): Boolean {
        return isPrepared
    }

    fun isFragmentVisible(): Boolean {
        return isFragmentVisible
    }

    fun isFirstLoad(): Boolean {
        return isFirstLoad
    }

}