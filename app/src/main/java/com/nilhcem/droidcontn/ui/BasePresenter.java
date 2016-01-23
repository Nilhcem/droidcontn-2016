package com.nilhcem.droidcontn.ui;

public abstract class BasePresenter<V> {

    protected final V view;

    public BasePresenter(V view) {
        this.view = view;
    }
}
