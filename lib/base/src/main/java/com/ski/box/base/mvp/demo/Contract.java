package com.ski.box.base.mvp.demo;


import com.ski.box.base.mvp.BaseModel;
import com.ski.box.base.mvp.BasePresenter;
import com.ski.box.base.mvp.BaseView;

public interface Contract {
    interface View extends BaseView {
    }

    abstract class Model extends BaseModel {
        public Model(boolean registerEvent) {
            super(registerEvent);
        }
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public Presenter(Model model, View view, boolean registerEvent) {
            super(model, view, registerEvent);
        }
    }
}
