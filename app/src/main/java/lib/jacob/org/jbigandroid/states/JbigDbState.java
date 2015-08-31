package lib.jacob.org.jbigandroid.states;

import java.util.List;

/**
 * Created by moses on 8/31/15.
 */
public interface JbigDbState extends BaseState {
    List<byte[]> getJbigDbs();
    byte[] getJbigAtPosition(int position);
}
