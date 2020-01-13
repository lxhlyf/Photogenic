package com.example.yxm.photogenic.module.hotwords

import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.HotWordsModel
import io.reactivex.disposables.Disposable

/**
 * Created by yxm on 2020-1-13
 * @function: 热搜presenter
 */
class HotWordsPresenter: BasePresenter<HotWordsContract.IHotWordsView>(),HotWordsContract.IHotWordsPresenter {

    private val hotWordsModel: HotWordsModel by lazy {
        HotWordsModel()
    }

    /**
     * 获取热搜数据
     */
    override fun getHotWords() {
        checkViewAttached()
        mRootView?.showLoading()
        val dispose = hotWordsModel.getHotWords()
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        setHotWords(it)
                    }
                },{
                    mRootView?.apply {
                        showError("获取热搜失败")
                    }
                })
        addSubscribe(dispose)
    }
}