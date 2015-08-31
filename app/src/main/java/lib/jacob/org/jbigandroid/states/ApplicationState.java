package lib.jacob.org.jbigandroid.states;

import com.google.common.base.Preconditions;

import com.squareup.otto.Bus;

import java.util.List;

/**
 * Created by moses on 8/31/15.
 */
public final class ApplicationState implements BaseState, JbigDbState {

    private final Bus mEventBus;

    public ApplicationState(Bus eventBus) {
        mEventBus = Preconditions.checkNotNull(eventBus, "EventBus cannot be null");
    }

    @Override
    public List<byte[]> getJbigDbs() {
        return null;
    }

    @Override
    public byte[] getJbigAtPosition(int position) {
        return new byte[0];
    }

    @Override
    public void registerForEvents(Object object) {
        mEventBus.register(object);
    }

    @Override
    public void unregisterForEvents(Object object) {
        mEventBus.unregister(object);
    }
}
