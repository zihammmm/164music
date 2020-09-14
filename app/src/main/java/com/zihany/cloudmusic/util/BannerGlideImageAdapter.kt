package com.zihany.cloudmusic.util

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.zihany.cloudmusic.main.bean.BannerBean

class BannerGlideImageAdapter constructor(list: BannerBean)
    : BannerImageAdapter<BannerBean.BannersBean>(list.banners) {

    override fun onBindView(holder: BannerImageHolder?, data: BannerBean.BannersBean?, position: Int, size: Int) {
        if (holder == null || data == null) {
            return
        }
        Glide.with(holder.itemView)
                .load(data.pic)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(holder.imageView)
    }
}