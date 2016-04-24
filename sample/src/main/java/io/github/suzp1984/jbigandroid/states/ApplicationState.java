package io.github.suzp1984.jbigandroid.states;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import dagger.internal.Preconditions;
import io.github.suzp1984.jbigandroid.controller.MainController;
import io.github.suzp1984.jbigandroid.db.DataBaseHelper;

/**
 * Created by moses on 8/31/15.
 */
public final class ApplicationState implements BaseState, JbigDbState {

    private final Bus mEventBus;
    private final List<byte[]> mJbigList = new ArrayList<>();
    private final DataBaseHelper mDbHelper;

    private MainController.TabItem mSelectedTabItem;

    @Inject
    public ApplicationState(Bus eventBus, DataBaseHelper helper) {
        mEventBus = Preconditions.checkNotNull(eventBus, "EventBus cannot be null");
        mDbHelper = Preconditions.checkNotNull(helper, "DataBaseHelper cannot be null");

        mJbigList.addAll(mDbHelper.getJbigs());
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
        mDbHelper.put(jbig);
        mJbigList.add(jbig);
        mEventBus.post(new JbigDbAddEvent());
    }

    @Override
    public void putJbigs(Collection<byte[]> jbigs) {
        mDbHelper.put(jbigs);
        mJbigList.addAll(jbigs);
        mEventBus.post(new JbigDbAddEvent());
    }

    @Override
    public void deleteJbig(byte[] jbig) {
        // TODO. delete item.
    }

    @Override
    public MainController.TabItem getSelectedTabItem() {
        return mSelectedTabItem;
    }

    @Override
    public void setSelectedTabItem(MainController.TabItem item) {
        mSelectedTabItem = item;
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
