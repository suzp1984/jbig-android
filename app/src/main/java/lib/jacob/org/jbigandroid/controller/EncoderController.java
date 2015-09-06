package lib.jacob.org.jbigandroid.controller;

import com.squareup.otto.Subscribe;

import android.graphics.Bitmap;

import javax.inject.Inject;
import javax.inject.Singleton;

import lib.jacob.org.jbigandroid.states.JbigDbState;

/**
 * Created by moses on 9/6/15.
 */

@Singleton
public class EncoderController extends BaseUiController<EncoderController.EncoderUi,
        EncoderController.EncoderUiCallback> {

    private final JbigDbState mJbigDbState;

    @Inject
    public EncoderController(JbigDbState state) {
        mJbigDbState = state;
    }

    @Override
    EncoderUiCallback createUiCallbacks(EncoderUi ui) {
        return new EncoderUiCallback() {
            @Override
            public void encodeBitmap(Bitmap bitmap) {
                //TODO: encode the bitmap here.
            }
        };
    }

    @Override
    void onUiAttached(EncoderUi ui) {

    }

    @Override
    void onUiDetached(EncoderUi ui) {

    }

    @Override
    void populateUi(EncoderUi ui) {

    }

    @Override
    void onInited() {
        mJbigDbState.registerForEvents(this);
    }

    @Override
    void onSuspended() {
        mJbigDbState.unregisterForEvents(this);
    }

    @Subscribe
    public void onJbigDataAdd(JbigDbState.JbigDbAddEvent event) {
        // do nothing, pass
    }

    public interface EncoderUi extends BaseUiController.Ui<EncoderUiCallback> {
        // nothing?
    }

    public interface EncoderUiCallback {
        void encodeBitmap(Bitmap bitmap);
    }
}
