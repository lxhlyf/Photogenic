package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.bean.CategoriesBean
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable

/**
 *Created by yxm on 2019/12/31
 *@function 获取分类目录实体类
 */
class CategoriesModel {

    fun getCategoryData(): Observable<ArrayList<CategoriesBean>>{
        return RetrofitManager.getApi(UrlConstants.baseUrlKaiYan)
                .getCategories()
                .compose(IoMainScheduler())
    }

}