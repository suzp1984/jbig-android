package lib.jacob.org.jbigandroid.controller;

import com.google.common.base.Preconditions;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;

import lib.jacob.org.jbigandroid.db.DataBaseHelper;
import lib.jacob.org.jbigandroid.states.JbigDbState;

/**
 * Created by moses on 9/6/15.
 */

@Singleton
public class DecoderController extends BaseUiController<DecoderController.DecoderUi, DecoderController.DecoderUiCallback> {

    private final JbigDbState mJbigDbState;

    @Inject
    public DecoderController(JbigDbState state) {
        Preconditions.checkNotNull(state, "JbigDbState is not null.");

        mJbigDbState = state;
    }

    @Override
    DecoderUiCallback createUiCallbacks(DecoderUi ui) {
        return new DecoderUiCallback() {
            @Override
            public void delete(int position) {

            }

            @Override
            public void add(byte[] jbig) {

            }
        };
    }

    @Override
    void onUiAttached(DecoderUi ui) {

    }

    @Override
    void onUiDetached(DecoderUi ui) {

    }

    @Override
    void populateUi(DecoderUi ui) {

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

    public interface DecoderUi extends BaseUiController.Ui<DecoderUiCallback> {
        void showJbig(byte[] jbig);
    }

    public interface DecoderUiCallback {
        void delete(int position);
        void add(byte[] jbig);
    }
}
