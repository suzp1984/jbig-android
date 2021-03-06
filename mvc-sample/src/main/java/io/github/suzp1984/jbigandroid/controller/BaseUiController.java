package io.github.suzp1984.jbigandroid.controller;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import dagger.internal.Preconditions;

/**
 * Created by moses on 9/1/15.
 */
abstract class BaseUiController<U extends BaseUiController.Ui<UC>, UC>
        extends BaseController {

    private final Set<U> mUis;
    private final Set<U> mUnmodifiableUis;

    public BaseUiController() {
        mUis = new CopyOnWriteArraySet<>();
        mUnmodifiableUis = Collections.unmodifiableSet(mUis);
    }

    abstract UC createUiCallbacks(U ui);
    abstract void onUiAttached(U ui);
    abstract void onUiDetached(U ui);
    abstract void populateUi(U ui);

    public synchronized final void attachUi(U ui) {
        Preconditions.checkNotNull(ui, "ui cannot be null");
        if (mUis.contains(ui)) {
            throw new RuntimeException("Ui is already attached");
        }

        mUis.add(ui);
        ui.setCallback(createUiCallbacks(ui));

        if (isInited()) {
            onUiAttached(ui);
            populateUi(ui);
        }
    }

    public synchronized final void detachUi(U ui) {
        Preconditions.checkNotNull(ui, "ui cannot be null");
        if (!mUis.contains(ui)) {
            throw new RuntimeException("ui is not attached.");
        }

        onUiDetached(ui);
        ui.setCallback(null);

        mUis.remove(ui);
    }

    public synchronized final void populateUis() {
        for (U ui : mUis) {
            populateUi(ui);
        }
    }

    public interface Ui<UC> {
        void setCallback(UC callback);
        boolean isModal();
    }
}
