package lib.jacob.org.jbigandroid.states;

import com.google.common.base.Preconditions;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by moses on 8/31/15.
 */
public final class ApplicationState implements BaseState, JbigDbState {

    private final Bus mEventBus;
    private final List<byte[]> mJbigList = new ArrayList<>();

    public ApplicationState(Bus eventBus) {
        mEventBus = Preconditions.checkNotNull(eventBus, "EventBus cannot be null");
    }

    @Override
    public List<byte[]> getJbigDbs() {
        return mJbigList;
    }

    @Override
    public byte[] getJbigAtPosition(int position) {
        return mJbigList.get(position);
    }

    @Override
    public void putJbig(byte[] jbig) {
        mJbigList.add(jbig);
    }

    @Override
    public void putJbigs(Collection<byte[]> jbigs) {
        mJbigList.addAll(jbigs);
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
