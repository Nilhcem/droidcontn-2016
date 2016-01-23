package com.nilhcem.droidcontn.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseFragment<P extends BaseFragmentPresenter> extends Fragment {

    protected P presenter;

    protected abstract P newPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = newPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (presenter != null) {
            presenter.onViewCreated(view, savedInstanceState);
        }
   }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.onDestroyView();
        }
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
