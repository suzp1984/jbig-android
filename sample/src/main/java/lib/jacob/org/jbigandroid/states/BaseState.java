package lib.jacob.org.jbigandroid.states;

import lib.jacob.org.jbigandroid.controller.MainController;

/**
 * Created by moses on 8/31/15.
 */
public interface BaseState {

    MainController.TabItem getSelectedTabItem();

    void setSelectedTabItem(MainController.TabItem item);

    void registerForEvents(Object object);

    void unregisterForEvents(Object object);
}
