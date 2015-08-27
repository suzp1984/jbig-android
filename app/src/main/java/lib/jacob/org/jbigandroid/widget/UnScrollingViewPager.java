package lib.jacob.org.jbigandroid.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by moses on 8/27/15.
 */
public class UnScrollingViewPager extends ViewPager {

    public UnScrollingViewPager(Context context) {
        super(context);
    }

    public UnScrollingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
