package com.example.yxm.photogenic.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.lib_imageloader.ImageLoaderManager
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseImmersionFragment
import com.example.yxm.photogenic.module.webview.WebViewActivity
import com.example.yxm.photogenic.ui.activity.FeedBackActivity
import com.example.yxm.photogenic.ui.activity.LoginActivity
import com.example.yxm.photogenic.utils.PackageHelper
import com.example.yxm.photogenic.widget.CircleImageView
import com.gyf.immersionbar.ImmersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import share.core.ShareManager

/**
 * Created by yxm on 2020-1-14
 * @function:我的fragment
 */
class MineFragment : BaseImmersionFragment(), View.OnClickListener {

    private lateinit var mAvatar: CircleImageView
    private lateinit var mGitTv: TextView
    private lateinit var mContact: TextView
    private lateinit var mJoinTv: TextView
    private lateinit var mVersion: TextView
    private lateinit var mClearCache: TextView
    private lateinit var mShareTv: TextView

    private var isLogin = false

    override fun initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(status_bar_view)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.statusBarColor)
                .init()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View) {
        mAvatar = user_avatar_iv
        mGitTv = git_hub_tv
        mContact = email_tv
        mJoinTv = join_tv
        mVersion = version_tv
        mClearCache = clear_cache_tv
        mShareTv = share_app_tv
        EventBus.getDefault().register(this)
    }

    override fun initListener() {
        mShareTv.setOnClickListener(this)
        mAvatar.setOnClickListener(this)
        mGitTv.setOnClickListener(this)
        mContact.setOnClickListener(this)
        mJoinTv.setOnClickListener(this)
        mVersion.setOnClickListener(this)
        mClearCache.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun lazyLoad() {
        mVersion.text = "Version ${PackageHelper.getAppVersion()}"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.user_avatar_iv -> {
                //跳转登录界面
                if (!isLogin) {
                    startActivity(Intent(mContext, LoginActivity::class.java))
                }
            }
            R.id.git_hub_tv -> {
                val url = "https://github.com/yxmFromTheMoon"
                WebViewActivity.start(mContext, resources.getString(R.string.git_title), url)
            }
            R.id.email_tv -> {
                startActivity(Intent(mContext, FeedBackActivity::class.java))
            }
            R.id.join_tv -> {
                val url = "http://open.eyepetizer.net/#!/landing"
                WebViewActivity.start(mContext, resources.getString(R.string.kaiyan_title), url)
            }
            R.id.clear_cache_tv -> {
                //清除视频缓存
                GSYVideoManager.instance().clearAllDefaultCache(mContext)
                //清除图片缓存
                ImageLoaderManager.clearAllImageCache(mContext)
                Toast.makeText(mContext, "清除缓存成功", Toast.LENGTH_SHORT).show()
            }
            R.id.share_app_tv -> {
                ShareManager.shareWebPage(mContext, resources.getString(R.string.share_text)
                        , resources.getString(R.string.share_title)
                        , resources.getString(R.string.share_image),
                        "https://blog.csdn.net/qq_37029648/article/details/104790963")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 伴生对象
     */
    companion object {
        /**
         * 返回一个fragment实例
         */
        fun newInstance(): MineFragment = MineFragment()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun userLogin(event: String) {
        isLogin = true
        mAvatar.setImageResource(R.drawable.account_default_avatar)
        login_guide.text = event
    }
}