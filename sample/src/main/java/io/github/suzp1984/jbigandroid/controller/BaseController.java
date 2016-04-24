package io.github.suzp1984.jbigandroid.controller;

import dagger.internal.Preconditions;
import io.github.suzp1984.jbigandroid.display.IDisplay;

/**
 * Created by moses on 8/31/15.
 */
abstract class BaseController {

    private IDisplay mDisplay;
    private boolean mInited;

    public final void init() {
        if (mInited == true) {
            throw new RuntimeException("Already inited");
        }

        mInited = true;
        onInited();
    }

    public final void suspend() {
        if (mInited == false) {
            throw new RuntimeException("Not Inited");
        }

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
