package com.ski.box.account.mvp.login;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.ski.box.base.mvp.BaseModel;
import com.ski.box.base.mvp.BasePresenter;
import com.ski.box.base.mvp.BaseView;


/**
 * date : 2020-02-12
 * desc :
 */
public interface LoginContract {

    abstract class Model extends BaseModel {

    }

    interface View extends BaseView {
        Activity getActivity();

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new LoginModel(), view);
        }

    }
}
