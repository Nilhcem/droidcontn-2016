package com.nilhcem.droidcontn.ui.speakers.detail;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.nilhcem.droidcontn.R;

public class SpeakerDetailLinkIcon extends ImageView {

    private final int mDefaultColor;
    private final int mPressedColor;
    private Rect mRect;

    public SpeakerDetailLinkIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPressedColor = ContextCompat.getColor(context, R.color.primary_text);
        mDefaultColor = ContextCompat.getColor(context, android.R.color.white);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPressedColor();
                break;
            case MotionEvent.ACTION_UP:
                setDefaultColor();
                break;
            case MotionEvent.ACTION_MOVE:
                // User moved outside bounds
                if (mRect != null && !mRect.contains(getLeft() + (int) event.getX(), getTop() + (int) event.getY())) {
                    setDefaultColor();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setDefaultColor() {
        setColorFilter(mDefaultColor);
        mRect = null;
    }

    private void setPressedColor() {
        setColorFilter(mPressedColor);
        mRect = new Rect(getLeft(), getTop(), getRight(), getBottom());
    }
}
