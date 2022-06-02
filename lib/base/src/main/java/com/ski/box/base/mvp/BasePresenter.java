package com.ski.box.base.mvp;

import androidx.annotation.Nullable;



/**
 * desc :
 */
public abstract class BasePresenter<Model extends BaseModel, View extends BaseView> {
    private final Model model;
    private final View view;
    private final boolean registerEvent;

    public BasePresenter(@Nullable Model model, @Nullable View view, boolean registerEvent) {
        this.model = model;
        this.view = view;
        this.registerEvent = registerEvent;
        if (registerEvent) {

        }
    }

    public BasePresenter(@Nullable Model model, @Nullable View view) {
        this(model, view, false);
    }

    public View getView() {
        return view;
    }

    public Model getModel() {
        return model;
    }

    public void detachView() {
        if (model != null) {
            model.detachModel();
        }

        if (registerEvent) {

        }
    }
}