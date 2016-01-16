package com.nilhcem.droidcontn.ui;

public abstract class BasePresenter<V> {

    protected final V mView;

    public BasePresenter(V view) {
        mView = view;
    }
}
