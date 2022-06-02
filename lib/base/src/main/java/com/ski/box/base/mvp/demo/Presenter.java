package com.ski.box.base.mvp.demo;

public class Presenter extends Contract.Presenter {
    public Presenter(Contract.View view) {
        super(new Model(), view, false);
    }
}
