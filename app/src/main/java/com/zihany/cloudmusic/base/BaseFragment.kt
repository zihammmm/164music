package com.zihany.cloudmusic.base

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.zihany.cloudmusic.widget.LoadingDialog

abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment(layoutId) {
    companion object {
        const val TAG = "BaseFragment"
        const val SONG_URL = "http://music.163.com/song/media/outer/url?id="
    }

    lateinit var binding: T

    private lateinit var diaLog: LoadingDialog
    protected lateinit var activity: AppCompatActivity
    var fragmentTitle: String? = null
    private var isFragmentVisible = false
    private var isFirstLoad = true
    private var forceLoad = false
    private var isPrepared = false

    protected fun <T : ViewDataBinding> binding(
            inflater: LayoutInflater,
            @LayoutRes layoutId: Int,
            container: ViewGroup?
    ): T = DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
        lifecycleOwner = this@BaseFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDialog()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isFirstLoad = true
        isPrepared = true
        binding = binding(inflater, layoutId, container)
        lazyLoad()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isPrepared = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        startObserve()
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    protected abstract fun initView()
    protected abstract fun startObserve()
    protected abstract fun initData()
    protected abstract fun onClick(view: View)

    protected fun lazyLoad() {
        if (isPrepared && isFragmentVisible) {
            if (forceLoad || isFirstLoad) {
                isFirstLoad = false
                forceLoad = false
                initData()
            }
        }
    }

    protected open fun onVisible() {
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
        return
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