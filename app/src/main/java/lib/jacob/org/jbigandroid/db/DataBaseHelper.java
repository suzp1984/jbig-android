package lib.jacob.org.jbigandroid.db;

import java.util.Collection;
import java.util.List;

/**
 * Created by moses on 8/31/15.
 */
public interface DatabaseHelper {
    List<byte[]> getJbigs();

    byte[] getJbig(int position);

    void put(byte[] jbig);

    void put(Collection<byte[]> jbigs);

    void delete(int position);

    void deleteAll();
}
