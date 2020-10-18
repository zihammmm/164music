package com.zihany.cloudmusic.personal.mvvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseActivity
import com.zihany.cloudmusic.base.BaseFragment
import com.zihany.cloudmusic.databinding.ActivityPersonalInfoBinding
import com.zihany.cloudmusic.login.bean.LoginBean
import com.zihany.cloudmusic.login.bean.Profile
import com.zihany.cloudmusic.main.adapter.MultiFragmentPagerAdapter
import com.zihany.cloudmusic.main.mvvm.view.MainActivity
import com.zihany.cloudmusic.personal.mvvm.view.fragment.UserDynamicsFragment
import com.zihany.cloudmusic.personal.mvvm.view.fragment.UserInfoFragment
import com.zihany.cloudmusic.personal.mvvm.view.fragment.UserPlayListFragment
import com.zihany.cloudmusic.personal.mvvm.viewmodel.PersonalViewModel
import com.zihany.cloudmusic.search.bean.UserSearchBean
import com.zihany.cloudmusic.util.*
import jp.wasabeef.glide.transformations.BlurTransformation
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonalInfoActivity : BaseActivity() {
    companion object {
        private const val TAG = "PersonalInfoActivity"
        const val USER_BEAN = "userBean"
    }

    private val pagerAdapter by lazy { MultiFragmentPagerAdapter(supportFragmentManager) }
    private var deltaDistance = 0f
    private var minDistance = 0f
    private var coverUrl = ""
    private var userSearchBean: UserSearchBean.ResultBean.UserprofilesBean? = null
    private var fragments = ArrayList<BaseFragment<*>>()
    private var loginBean: Profile? = null
    private val userInfoFragment by lazy { UserInfoFragment() }
    private val userPlayListFragment by lazy { UserPlayListFragment() }
    private val userDynamicsFragment by lazy { UserDynamicsFragment() }

    private val viewModel by viewModel<PersonalViewModel>()
    private val binding by binding<ActivityPersonalInfoBinding>(R.layout.activity_personal_info)

    override fun initData() {
        setBackBtn(getString(R.string.colorWhite))

        if (intent.getStringExtra(MainActivity.LOGIN_BEAN) != null) {
            binding.btnEdit.visibility = View.VISIBLE
            val loginProfileBean = intent.getStringExtra(MainActivity.LOGIN_BEAN)
            loginBean = GsonUtil.fromJSON<Profile>(loginProfileBean)
            setMyInfoBean()

        } else if (intent.getStringExtra(USER_BEAN) != null) {
            userInfoFragment.fragmentTitle = getString(R.string.about_ta)
            val otherProfileBean = intent.getStringExtra(USER_BEAN)
            userSearchBean = GsonUtil.fromJSON<UserSearchBean.ResultBean.UserprofilesBean>(otherProfileBean)
            addDataToPersonalInfo()

        }

        pagerAdapter.getItem(0).userVisibleHint = true

        minDistance = dp2px(85f).toFloat()
        deltaDistance = dp2px(200f).toFloat() - minDistance
    }

    override fun initView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        fragments.add(userPlayListFragment)
        fragments.add(userDynamicsFragment)
        fragments.add(userInfoFragment)
    }

    override fun startObserve() {
        viewModel.run {

        }
    }

    override fun onClick(view: View) {
        if (view.isFastClick(1000)) {
            return
        }
        val intent = Intent()
        when(view.id) {
            R.id.btn_edit -> {
                intent.setClass(this, PersonalSettingActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_avatar -> {
                intent.setClass(this, PictureCheckActivity::class.java)
                if (loginBean != null) {
                    intent.putExtra(PictureCheckActivity.PIC_URL, loginBean!!.avatarUrl)
                } else {
                    intent.putExtra(PictureCheckActivity.PIC_URL, userSearchBean!!.avatarUrl)
                }
                startActivity(intent)
            }
        }
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

}