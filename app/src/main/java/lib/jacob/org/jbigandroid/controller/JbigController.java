package lib.jacob.org.jbigandroid.controller;

import com.google.common.base.Preconditions;

import com.squareup.otto.Subscribe;

import android.graphics.Bitmap;

import javax.inject.Inject;
import javax.inject.Singleton;

import lib.jacob.org.jbigandroid.states.JbigDbState;

/**
 * Created by moses on 9/6/15.
 */

@Singleton
public class JbigController extends
        BaseUiController<JbigController.JbigControllerUi, JbigController.JbigUiCallback> {

    private final JbigDbState mJbigDbState;

    @Inject
    public JbigController(JbigDbState state) {
        Preconditions.checkNotNull(state, "JbigDbState is not null.");

        mJbigDbState = state;
    }

    @Override
    JbigUiCallback createUiCallbacks(JbigControllerUi ui) {
        return new JbigUiCallback() {
            @Override
            public void encodeBitmap(Bitmap bitmap) {

            }

            @Override
            public void delete(int position) {

            }

            @Override
            public void add(byte[] jbig) {

            }
        };
    }

    @Override
    void onUiAttached(JbigControllerUi ui) {

    }

    @Override
    void onUiDetached(JbigControllerUi ui) {

    }

    @Override
    void populateUi(JbigControllerUi ui) {
        if (ui instanceof JbigEncoderUi) {
            populateUi((JbigEncoderUi) ui);
        } else if (ui instanceof  JbigDecoderUi) {
            populateUi((JbigDecoderUi) ui);
        }
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
        populateUis();
    }

    private void populateUi(JbigDecoderUi ui) {
        for (byte[] jbig : mJbigDbState.getJbigDbs()) {
            ui.showJbig(jbig);
        }
    }

    private void populateUi(JbigEncoderUi ui) {
        // nothing, pass
    }

    public interface JbigControllerUi extends BaseUiController.Ui<JbigUiCallback> {

    }

    public interface JbigDecoderUi extends JbigControllerUi {
        void showJbig(byte[] jbig);
    }

    public interface JbigEncoderUi extends JbigControllerUi {
        // nothing?
    }

    public interface JbigUiCallback {
        void encodeBitmap(Bitmap bitmap);
        void delete(int position);
        void add(byte[] jbig);
    }
}
