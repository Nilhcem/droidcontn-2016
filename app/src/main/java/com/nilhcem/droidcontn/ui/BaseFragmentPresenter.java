package com.nilhcem.droidcontn.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public abstract class BaseFragmentPresenter<V> extends BasePresenter<V> {

    public BaseFragmentPresenter(V view) {
        super(view);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Nothing to do by default
    }

    public void onDestroyView() {
        // Nothing to do by default
    }
}
