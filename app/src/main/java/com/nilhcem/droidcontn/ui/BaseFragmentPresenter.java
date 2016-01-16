package com.nilhcem.droidcontn.ui;

public abstract class BaseFragmentPresenter<V> {

    protected final V mView;

    public BaseFragmentPresenter(V view) {
        mView = view;
    }
}
