package com.ski.box.newconstruct.launcher.mvp.launcher;


import com.ski.box.base.mvp.BaseModel;
import com.ski.box.base.mvp.BasePresenter;
import com.ski.box.base.mvp.BaseView;
import com.ski.box.common.page.TioActivity;

/**
 * date : 2020-02-12
 * desc :
 */
public interface LauncherContract {
    interface View extends BaseView {
        void openLoginPage();

        void finish();

        void openMainPage();

        TioActivity getActivity();
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(View view) {
            super(new LauncherModel(), view);
        }

        public abstract void init();
    }
}
