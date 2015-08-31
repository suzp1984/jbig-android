package lib.jacob.org.jbigandroid.states;

/**
 * Created by moses on 8/31/15.
 */
public interface BaseState {

    void registerForEvents(Object object);

    void unregisterForEvents(Object object);
}
