package lib.jacob.org.jbigandroid.realmobj;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by moses on 8/28/15.
 */
public class JbigItem extends RealmObject {

    private String tag;

    private byte[] jbig;

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setJbig(byte[] jbig) {
        this.jbig = jbig;
    }

    public String getTag() {
        return tag;
    }

    public byte[] getJbig() {
        return jbig;
    }
}
