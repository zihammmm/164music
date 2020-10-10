package com.zihany.cloudmusic.personal.mvvm.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.databinding.ActivityAvatarCheckBinding
import com.zihany.cloudmusic.personal.mvvm.viewmodel.PictureCheckViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PictureCheckActivity: BaseActivity() {
    companion object {
        const val TAG = "PictureCheckActivity"
        const val PIC_URL = "picUrl"
    }
    private var avatarUrl: String? = null
    private var bitmap: Bitmap? = null

    private lateinit var binding: ActivityAvatarCheckBinding

    override fun initData() {
        intent.getStringExtra(PIC_URL)?.let {
            Glide.with(this)
                    .asBitmap()
                    .load(it)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            bitmap = resource
                            binding.ivAvatar.setImage(bitmap!!)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {

                        }

                    })
        }
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

    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch(Dispatchers.IO) {
            Glide.get(this@PictureCheckActivity)
                    .clearDiskCache()
        }
        Glide.get(this)
                .clearMemory()
    }

}