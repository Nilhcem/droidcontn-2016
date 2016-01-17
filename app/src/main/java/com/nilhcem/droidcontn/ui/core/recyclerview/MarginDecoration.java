package com.nilhcem.droidcontn.ui.core.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nilhcem.droidcontn.R;

public class MarginDecoration extends RecyclerView.ItemDecoration {

    private int mMargin;

    public MarginDecoration(Context context) {
        mMargin = context.getResources().getDimensionPixelSize(R.dimen.item_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mMargin, mMargin, mMargin, mMargin);
    }
}
