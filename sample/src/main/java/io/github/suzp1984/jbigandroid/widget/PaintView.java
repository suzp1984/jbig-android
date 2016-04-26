package io.github.suzp1984.jbigandroid.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by moses on 8/27/15.
 */
public class PaintView extends View {

    private Paint mPaint;
    private Canvas mCacheCanvas;
    private Bitmap mCachebBitmap;
    private Path mPath;

    private float mCur_x, mCur_y;

    private int mOrientation;

    private int mWidth;
    private int mHeight;

    private static final float TOUCH_TOLERANCE = 4;

    public PaintView(Context context) {
        super(context);

        mOrientation = context.getResources().getConfiguration().orientation;

        init();
    }

    public Bitmap getCachebBitmap() {
        return mCachebBitmap;
    }

    public void clear() {
        if (mCacheCanvas != null) {
            mPaint.setColor(Color.rgb(245, 245, 245));
            mPaint.setARGB(255, 75, 75, 73);
            mCachebBitmap.eraseColor(Color.TRANSPARENT);
            mCacheCanvas.drawColor(Color.TRANSPARENT);
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.e("PaintView",
                "width = " + w + "; hight = " + h + "; olderW = " + oldw + "; olderH = " + oldh);

        mWidth = w;
        mHeight = h;
        mCachebBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCacheCanvas = new Canvas(mCachebBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mCachebBitmap, 0, 0, null);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Log.e("PaintView", event.toString());

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                touch_start(x, y);
                invalidate();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                touch_move(x, y);
                invalidate();
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                touch_up();
                invalidate();
                break;
            }
        }

        return true;
    }

    @Override
    protected void onRestoreInstanceState (Parcelable state) {
        /*Parcel parcel = Parcel.obtain();
        state.writeToParcel(parcel, 0);
        mCachebBitmap = Bitmap.CREATOR.createFromParcel(parcel);
        invalidate();*/
        super.onRestoreInstanceState(state);
    }

    @Override
    protected Parcelable onSaveInstanceState () {
        return super.onSaveInstanceState();
        // return mCachebBitmap;
    }

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mCur_x = x;
        mCur_y = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mCur_x);
        float dy = Math.abs(y - mCur_y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mCur_x, mCur_y, (x + mCur_x)/2, (y + mCur_y)/2);
            mCur_x = x;
            mCur_y = y;
        }
    }
    private void touch_up() {
        mPath.lineTo(mCur_x, mCur_y);
        // commit the path to our offscreen
        mCacheCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
        mPath.reset();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();

        mPaint.setColor(Color.rgb(245, 245, 245));
        mPaint.setARGB(255, 75, 75, 73);
    }

}
