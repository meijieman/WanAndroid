package com.example.wanandroid.ui.article;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;
import com.example.wanandroid.R;
import com.example.wanandroid.base.App;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.bean.DataResponse;
import com.example.wanandroid.constant.Constant;
import com.example.wanandroid.net.ApiService;
import com.example.wanandroid.net.RetrofitManager;
import com.example.wanandroid.ui.my.LoginActivity;
import com.example.wanandroid.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/1.
 */

class ArticleContentPresenter extends BasePresenter<ArticleContact.View> {
    @Inject
    public ArticleContentPresenter() {
    }

    public void collectOutsideArticle(String title, String author, String url) {
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)){
            RetrofitManager.create(ApiService.class).addCollectOutsideArticle(title,author,url).compose(RxSchedulers.<DataResponse>applySchedulers()).compose(mView.<DataResponse>bindToLife()).subscribe(new Consumer<DataResponse>() {
                @SuppressLint("StringFormatInvalid")
                @Override
                public void accept(DataResponse response) throws Exception {
                    if (response.getErrorCode() == 0) {
                        mView.showSuccess(App.getAppContext().getString(R.string.collection_success));
                    } else {
                        mView.showFailed(App.getAppContext().getString(R.string.collection_failed, response.getErrorMsg()));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.showFailed(throwable.getMessage());
                }
            });
        }else {
            LoginActivity.start();

        }
    }

    public void collectArticle(int id) {
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)){
            RetrofitManager.create(ApiService.class).addCollectArticle(id).compose(RxSchedulers.<DataResponse>applySchedulers()).compose(mView.<DataResponse>bindToLife()).subscribe(new Consumer<DataResponse>() {
                @SuppressLint("StringFormatInvalid")
                @Override
                public void accept(DataResponse response) throws Exception {
                    if (response.getErrorCode() == 0) {
                        mView.showSuccess(App.getAppContext().getString(R.string.collection_success));
                    } else {
                        mView.showFailed(App.getAppContext().getString(R.string.collection_failed, response.getErrorMsg()));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.showFailed(throwable.getMessage());
                }
            });
        }else {
            LoginActivity.start();
        }
    }
}
