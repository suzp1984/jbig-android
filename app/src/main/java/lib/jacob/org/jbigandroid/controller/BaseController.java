package lib.jacob.org.jbigandroid.controller;


import com.google.common.base.Preconditions;

import lib.jacob.org.jbigandroid.display.IDisplay;

/**
 * Created by moses on 8/31/15.
 */
abstract class BaseController {

    private IDisplay mDisplay;
    private boolean mInited;

    public final void init() {
        Preconditions.checkState(mInited == false, "Already inited");

        mInited = true;
        onInited();
    }

    public final void suspend() {
        Preconditions.checkState(mInited == true, "Not inited");
        onSuspended();
        mInited = false;
    }

    public final boolean isInited() {
        return mInited;
    }

    protected void setDisplay(IDisplay display) {
        mDisplay = display;
    }

    protected final IDisplay getDisplay() {
        return mDisplay;
    }

    abstract void onInited();
    abstract void onSuspended();
}
