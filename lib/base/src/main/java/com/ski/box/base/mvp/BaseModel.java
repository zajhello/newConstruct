package com.ski.box.base.mvp;


/**
 * date : 2020-02-12
 * desc :
 */
public abstract class BaseModel {
    private final boolean registerEvent;

    public BaseModel(boolean registerEvent) {
        this.registerEvent = registerEvent;
        if (registerEvent) {

        }
    }

    public BaseModel() {
        this(false);
    }

    public void detachModel() {

        if (registerEvent) {

        }
    }

    // ====================================================================================
    // 数据回调
    // ====================================================================================

    public abstract static class DataProxy<Data> {
        public void onSuccess(Data data) {

        }

        public void onFailure(String msg) {

        }

        public void onFinish() {

        }
    }
}