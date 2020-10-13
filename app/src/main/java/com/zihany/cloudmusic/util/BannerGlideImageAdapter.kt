package com.zihany.cloudmusic.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.zihany.cloudmusic.main.bean.Banner
import com.zihany.cloudmusic.main.bean.BannerBean

class BannerGlideImageAdapter constructor(list: BannerBean)
    : BannerImageAdapter<Banner>(list.banners) {

    companion object {
        const val TAG = "BannerGlideImageAdapter"
    }

    override fun onBindView(holder: BannerImageHolder?, data: Banner?, position: Int, size: Int) {
        if (holder == null || data == null) {
            return
        }
        LogUtil.d(TAG, "imagerUrl: ${data.pic}")
        holder.imageView.scaleType = ImageView.ScaleType.FIT_XY
        Glide.with(holder.itemView)
                .load(data.pic)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(holder.imageView)
    }
}