package com.example.lib_imageloader

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *Created by yxm on 2020/1/3
 *@function 图片加载类
 */
object ImageLoaderManager {

    /**
     * * 为view加载图片
     * @param imageView
     * @param url
     */
    fun displayImageForView(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
                .asBitmap()
                .load(url)
                .apply(initCommonRequestOptions())
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(imageView)
    }

    /**
     * 为view加载圆形图片
     *
     * @param imageView
     * @param url
     */
    fun displayImageForCircle(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
                .asBitmap()
                .load(url)
                .apply(initCommonRequestOptions())
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap?) {
                        val drawable = RoundedBitmapDrawableFactory.create(imageView.resources, resource)
                        drawable.isCircular = true
                        imageView.setImageDrawable(drawable)
                    }
                })
    }

    /**
     * 为viewGroup加载背景并模糊处理
     *
     * @param viewGroup
     * @param url
     */
    fun displayImageForViewGroup(viewGroup: ViewGroup,url: String){
        Glide.with(viewGroup.context)
                .asBitmap()
                .load(url)
                .apply(initCommonRequestOptions())
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val disposable = Observable.just(resource)
                                .map {
                                    //为bitmap作模糊处理
                                    BitmapDrawable(Utils.doBlur(resource, 100, true))
                                }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { drawable ->
                                    viewGroup.background = drawable
                                }
                    }
                })
    }

    /**
     * init Options
     * @return
     */
     fun initCommonRequestOptions(): RequestOptions {
        val options = RequestOptions()
        options.placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
        return options
    }

}