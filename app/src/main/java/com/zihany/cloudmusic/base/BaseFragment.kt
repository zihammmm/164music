package com.zihany.cloudmusic.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zihany.cloudmusic.widget.LoadingDialog

abstract class BaseFragment<T: BaseViewModel> : Fragment() {
    companion object {
        const val TAG = "BaseFragment"
        const val SONG_URL = "http://music.163.com/song/media/outer/url?id="
    }

    protected lateinit var diaLog: LoadingDialog
    protected lateinit var activity: Activity
    protected lateinit var viewModel: T
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isFirstLoad = true
        isPrepared = true
        val view = initView(inflater, container, savedInstanceState)
        lazyLoad()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isPrepared = false
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

    protected fun onVisible() {
        isFragmentVisible = true
        lazyLoad()
    }

    protected fun onInvisible() {
        isFragmentVisible = false
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onVisible()
        }else {
            onInvisible()
        }
    }

    private fun createDialog() {
        if (!this::diaLog.isInitialized) {
            diaLog = LoadingDialog(context!!, "Loading...")
        }
    }

    fun showDialog() {
        if (this::diaLog.isInitialized && !diaLog.isShowing) {
            diaLog.show()
        }
    }

    fun hideDialog() {
        if(this::diaLog.isInitialized && diaLog.isShowing) {
            diaLog.dismiss()
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

    fun refreshData() {
        if (isFragmentVisible()) {
            initData()
        } else {
            setForceLoad(true)
        }
    }

}