package com.nilhcem.droidcontn.ui.venue;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.ui.BaseFragment;
import com.nilhcem.droidcontn.ui.BasePresenter;
import com.nilhcem.droidcontn.utils.Intents;
import com.nilhcem.droidcontn.utils.Views;

import butterknife.Bind;
import butterknife.OnClick;

public class VenueFragment extends BaseFragment {

    private static final float PHOTO_RATIO = 0.404f;
    private static final String COORDINATES_URI = "geo:36.369198,10.534286?q=" + Uri.encode("Medina Yasmine Hammamet, Tunisia");

    public static VenueFragment newInstance() {
        return new VenueFragment();
    }

    @Bind(R.id.venue_image) ImageView mPhoto;

    @Override
    protected BasePresenter newPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.venue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPhotoSize();
    }

    @OnClick(R.id.venue_locate)
    void openMapsLocation() {
        if (!Intents.startUri(getContext(), COORDINATES_URI)) {
            View view = getView();
            if (view != null) {
                Snackbar.make(view, R.string.venue_see_location_error, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void initPhotoSize() {
        mPhoto.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = mPhoto.getWidth();
                if (width != 0) {
                    Views.removeOnGlobalLayoutListener(mPhoto.getViewTreeObserver(), this);
                    mPhoto.getLayoutParams().height = Math.round(width * PHOTO_RATIO);
                    mPhoto.requestLayout();
                }
            }
        });
    }
}
