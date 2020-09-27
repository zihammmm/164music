package com.zihany.cloudmusic.personal.mvvm.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.ActivityPersonalInfoBinding
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.main.mvvm.view.MainActivity
import com.zihany.cloudmusic.personal.mvvm.view.fragment.UserDynamicsFragment
import com.zihany.cloudmusic.personal.mvvm.view.fragment.UserInfoFragment
import com.zihany.cloudmusic.personal.mvvm.view.fragment.UserPlayListFragment
import com.zihany.cloudmusic.personal.mvvm.viewmodel.PersonalViewModel
import com.zihany.cloudmusic.search.bean.UserSearchBean
import com.zihany.cloudmusic.util.AppBarStateChangeListener
import com.zihany.cloudmusic.util.DensityUtil
import com.zihany.cloudmusic.util.GsonUtil
import jp.wasabeef.glide.transformations.BlurTransformation

class PersonalInfoActivity : BaseActivity<PersonalViewModel>() {
    companion object {
        private const val TAG = "PersonalInfoActivity"
        const val USER_BEAN = "userBean"
    }

    private lateinit var binding: ActivityPersonalInfoBinding
    private lateinit var pagerAdapter: MultiFragmentPagerAdapter
    private var deltaDistance = 0f
    private var minDistance = 0f
    private var coverUrl = ""
    private var userSearchBean: UserSearchBean.ResultBean.UserprofilesBean? = null
    private var fragments = ArrayList<BaseFragment<*>>()
    private var loginBean: LoginBean.ProfileBean? = null
    private var userInfoFragment: UserInfoFragment? = null

    override fun initData() {
        setBackBtn(getString(R.string.colorWhite))

        if (intent.getStringExtra(MainActivity.LOGIN_BEAN) != null) {
            binding.btnEdit.visibility = View.VISIBLE
            val loginProfileBean = intent.getStringExtra(MainActivity.LOGIN_BEAN)
            loginBean = GsonUtil.fromJSON<LoginBean.ProfileBean>(loginProfileBean)
            setMyInfoBean()

        } else if (intent.getStringExtra(USER_BEAN) != null) {
            userInfoFragment?.fragmentTitle = getString(R.string.about_ta)
            val otherProfileBean = intent.getStringExtra(USER_BEAN)
            userSearchBean = GsonUtil.fromJSON<UserSearchBean.ResultBean.UserprofilesBean>(otherProfileBean)
            addDataToPersonalInfo()

        }

        binding.vpContainer.adapter = pagerAdapter
        binding.vpContainer.offscreenPageLimit = 3
        binding.vpContainer.currentItem = 0
        pagerAdapter.getItem(0).userVisibleHint = true
        binding.tabTitle.setViewPager(binding.vpContainer)

        minDistance = DensityUtil.dp2px(this, 85f).toFloat()
        deltaDistance = DensityUtil.dp2px(this, 200f).toFloat() - minDistance
    }

    override fun onResume() {
        super.onResume()

        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?) {
                val alphaPercent = (binding.rlInfo.top - minDistance) / deltaDistance
                val alpha = (alphaPercent * 255).toInt()
                binding.ivAvatar.alpha = alphaPercent
                binding.btnEdit.alpha = alphaPercent
                binding.ivBackgroundCover.imageAlpha = alpha
                binding.tvNickname.alpha = alphaPercent
                binding.tvLike.alpha = alphaPercent
                binding.tvFans.alpha = alphaPercent
                if (alphaPercent < 0.2f) {
                    val leftTitleAlpha = (1.0f - alphaPercent / 0.2f)
                    setLeftTitleAlpha(leftTitleAlpha)
                } else {
                    setLeftTitleAlpha(0f)
                }
            }

            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State) {
                if (state == State.COLLAPSED) {
                    setLeftTitleAlpha(255f)
                } else if (state == State.EXPANDED) {
                    binding.ivBackgroundCover.imageAlpha = 255
                    binding.ivAvatar.imageAlpha = 255
                    binding.btnEdit.alpha = 1f
                    binding.tvNickname.alpha = 1f
                    binding.tvLike.alpha = 1f
                    binding.tvFans.alpha = 1f
                }
            }
        })
    }

    private fun addDataToPersonalInfo() {
        setLeftTitleText(userSearchBean?.nickname.toString(), getString(R.string.colorWhite))
        setLeftTitleTextColorWhite()
        Glide.with(this)
                .load(userSearchBean?.avatarUrl)
                .into(binding.ivAvatar)
        coverUrl = userSearchBean?.backgroundUrl!!
        Glide.with(this)
                .load(coverUrl)
                .into(binding.ivBackgroundCover)
        Glide.with(this)
                .load(coverUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 1)))
                .into(binding.ivBackground)
        binding.tvNickname.text = userSearchBean?.nickname

    }

    @SuppressLint("SetTextI18n")
    private fun setMyInfoBean() {
        setLeftTitleText(loginBean!!.nickname, getString(R.string.colorWhite))
        setLeftTitleTextColorWhite()
        binding.tvLike.text = getString(R.string.follows) + loginBean?.follows
        binding.tvFans.text = getString(R.string.followeds) + loginBean?.followeds
        Glide.with(this)
                .load(loginBean?.avatarUrl)
                .into(binding.ivAvatar)
        coverUrl = loginBean?.backgroundUrl!!
        Glide.with(this)
                .load(coverUrl)
                .into(binding.ivBackgroundCover)
        Glide.with(this)
                .load(coverUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 1)))
                .into(binding.ivBackground)
        binding.tvNickname.text = loginBean?.nickname

    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivityPersonalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        pagerAdapter = MultiFragmentPagerAdapter(supportFragmentManager)
        fragments.add(UserPlayListFragment())
        fragments.add(UserDynamicsFragment())
        userInfoFragment = UserInfoFragment()
        fragments.add(userInfoFragment!!)
        pagerAdapter.init(fragments)
    }

}