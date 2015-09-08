
package lib.jacob.org.jbig;

import android.graphics.Bitmap;

/**
 * Created by moses on 8/5/14.
 */
public interface JbigCodec {
    byte[] encode(Bitmap[] bitmaps);
    Bitmap[] decode(byte[] data);
}
