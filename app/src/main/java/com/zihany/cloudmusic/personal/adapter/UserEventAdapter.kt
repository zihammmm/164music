package com.zihany.cloudmusic.personal.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lzx.starrysky.model.SongInfo
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseAdapter
import com.zihany.cloudmusic.databinding.ItemUserEventBinding
import com.zihany.cloudmusic.personal.bean.UserEventBean
import com.zihany.cloudmusic.personal.bean.UserEventJsonBean
import com.zihany.cloudmusic.personal.mvvm.view.PictureCheckActivity
import com.zihany.cloudmusic.song.mvvm.view.SongActivity
import com.zihany.cloudmusic.util.GsonUtil
import com.zihany.cloudmusic.util.LogUtil
import com.zihany.cloudmusic.util.TimeUtil
import com.zihany.cloudmusic.util.getTimeStandardOnlyYMD
import com.zihany.cloudmusic.video.mvvm.view.VideoActivity

class UserEventAdapter constructor(private val mContext: Context)
    : BaseAdapter<RecyclerView.ViewHolder, UserEventBean.EventsBean>(mContext) {
    companion object {
        const val TAG = "UserEventAdapter"
    }

    var isUserEvent = false
    var listener: OnEventClickListener? = null
    private var list = ArrayList<UserEventBean.EventsBean>()
    private lateinit var binding: ItemUserEventBinding

    override fun notifyDataSetChanged(dataList: ArrayList<UserEventBean.EventsBean>) {
        list = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemUserEventBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setBean(mContext, position)
            holder.setListener(listener, position)
        }
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val imgList: List<ImageView>

        init {
            imgList = listOf(binding.ivImg1,
                    binding.ivImg2,
                    binding.ivImg3,
                    binding.ivImg4,
                    binding.ivImg5,
                    binding.ivImg6,
                    binding.ivImg7,
                    binding.ivImg8,
                    binding.ivImg9)
        }

        fun setBean(context: Context, i: Int) {
            Glide.with(context)
                    .load(list[i].user?.avatarUrl)
                    .into(binding.ivAvatar)
            binding.tvNickname.text = list[i].user?.nickname
            binding.tvPublishTime.text = list[i].showTime.getTimeStandardOnlyYMD()
            binding.tvRelayoutCount.text = if (list[i].info?.shareCount == 0) {
                "转发"
            } else {
                list[i].info?.shareCount.toString()
            }
            binding.tvCommentCount.text = if (list[i].info?.commentCount == 0) {
                "评论"
            } else {
                list[i].info?.commentCount.toString()
            }
            binding.tvLikeCount.text = if (list[i].info?.likedCount == 0) {
                "点赞"
            } else {
                list[i].info?.likedCount.toString()
            }

            val json = list[i].json
            LogUtil.d(TAG, "json: $json")
            val jsonBean = GsonUtil.fromJSON<UserEventJsonBean>(json)
            jsonBean?.let {
                LogUtil.d(TAG, "jsonBean: $it")
                if (TextUtils.isEmpty(it.msg)) {
                    binding.tvContent.visibility = View.GONE
                } else {
                    binding.tvContent.visibility = View.VISIBLE
                    binding.tvContent.text = it.msg
                }
            }

            val type = list[i].info?.commentThread?.resourceInfo?.eventType ?: list[i].type
            showImg(context, i)
            showShareLayout(context, jsonBean)
            when (type) {
                18 -> binding.tvTitle.text = "分享单曲:"
                17, 28 -> {
                    binding.tvTitle.text = "分享电台:"
                    showDjBean(jsonBean)
                }
                22 -> {
                    binding.tvTitle.text = "转发:"
                }
                39 -> {
                    binding.tvTitle.text = "发布视频:"
                    showVideoBean(jsonBean)
                }
                else -> {

                }
            }
        }

        private fun showDjBean(jsonBean: UserEventJsonBean?) {

        }

        private fun showVideoBean(jsonBean: UserEventJsonBean?) {
            binding.rlVideo.visibility = View.VISIBLE
            binding.ivVid.visibility = View.VISIBLE
            jsonBean?.let {
                Glide.with(mContext)
                        .load(it.video?.coverUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivVid)
            }
        }

        private fun showImg(context: Context, position: Int) {
            list[position].pics?.let {
                if (it.isNotEmpty()) {
                    binding.rlImg.visibility = View.VISIBLE
                    LogUtil.d(TAG, "size: ${it.size}")
                    for (i: Int in it.indices) {
                        when (i) {
                            1 -> binding.llImgGroup1.visibility = View.VISIBLE
                            3 -> binding.llImgGroup2.visibility = View.VISIBLE
                            6 -> binding.llImgGroup3.visibility = View.VISIBLE
                            else -> {

                            }
                        }
                        Glide.with(context)
                                .load(it[i].rectangleUrl)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(imgList[i])
                    }
                }
            }
        }

        private fun showShareLayout(context: Context, jsonBean: UserEventJsonBean?) {
            if (!TextUtils.isEmpty(jsonBean?.song?.name)) {
                binding.rlShare.visibility = View.VISIBLE
                Glide.with(context)
                        .load(jsonBean!!.song!!.album?.picUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivSongCover)
                binding.tvCreatorName.text = jsonBean.song?.artists!![0].name
                binding.tvSongname.text = jsonBean.song?.name
            }else if (!TextUtils.isEmpty(jsonBean?.program?.name)) {
                binding.rlShare.visibility = View.VISIBLE
                Glide.with(context)
                        .load(jsonBean!!.program!!.coverUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(binding.ivSongCover)
                binding.tvCreatorName.text = jsonBean.program?.radio?.name
                binding.tvSongname.text = jsonBean.program?.name
            }else {
                binding.rlShare.visibility = View.GONE
            }
        }

        fun setListener(listener: OnEventClickListener?, position: Int) {
            val intent = Intent()
            for (i: Int in list.indices) {
                imgList[i].setOnClickListener {
                    intent.setClass(mContext, PictureCheckActivity::class.java)
                    intent.putExtra(PictureCheckActivity.PIC_URL, list[position].pics?.get(i)?.rectangleUrl)
                    mContext.startActivity(intent)
                }
            }
            val json = list[position].json
            val jsonBean = GsonUtil.fromJSON<UserEventJsonBean>(json)

            binding.rlShare.setOnClickListener {
                intent.setClass(mContext, SongActivity::class.java)
                val songInfo = SongInfo()
                jsonBean?.let {
                    if (it.song != null) {
                        val songBean = jsonBean.song!!
                        songInfo.duration = songBean.duration
                        songInfo.artist = songBean.artists?.get(0)?.name
                        songInfo.songId = songBean.id.toString()
                        songInfo.songUrl = "${BaseActivity.SONG_URL}${songInfo.songId}.mp3"
                        songInfo.songName = songBean.name
                        songInfo.songCover = songBean.album?.blurPicUrl
                    }else if (it.program != null) {
                        val programBean = jsonBean.program!!
                        songInfo.duration = programBean.duration
                        songInfo.artist = programBean.dj?.nickname
                        songInfo.songId = programBean.id.toString()
                        songInfo.songUrl = "${BaseActivity.SONG_URL}${programBean.id}.mp3"
                        songInfo.songName = programBean.name
                        songInfo.songCover = programBean.coverUrl
                    }
                    intent.putExtra(SongActivity.SONG_INFO, songInfo)
                    mContext.startActivity(intent)
                }
            }
            binding.rlVideo.setOnClickListener {
                intent.setClass(mContext, VideoActivity::class.java)
                mContext.startActivity(intent)
            }

            binding.ivAvatar.setOnClickListener {
                listener?.onAvatarClick(position)
            }

            binding.rlRelay.setOnClickListener {
                listener?.onRelayClick(position)
            }
        }
    }

    interface OnEventClickListener {
        fun onAvatarClick(position: Int)

        fun onRelayClick(position: Int)

        fun onCommentClick(position: Int)

        fun onLikeClick(position: Int)
    }


}