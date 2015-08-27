package lib.jacob.org.jbigandroid.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import lib.jacob.org.jbigandroid.R;

/**
 * Created by moses on 8/27/15.
 */
public class SwipeControlViewPager extends ViewPager {

    private boolean mSwipeable = true;

    public SwipeControlViewPager(Context context) {
        super(context);
    }

    public SwipeControlViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeControlViewPager);

        try {
            mSwipeable = a.getBoolean(R.styleable.SwipeControlViewPager_swipeable, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mSwipeable && super.onInterceptTouchEvent(ev);
    }

    public void setSwipeable(boolean swipeable) {
        mSwipeable = swipeable;
    }
}
