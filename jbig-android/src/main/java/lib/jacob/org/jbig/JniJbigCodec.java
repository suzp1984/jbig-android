
package lib.jacob.org.jbig;

import android.graphics.Bitmap;

/**
 * Created by moses on 8/5/14.
 */
public class JniJbigCodec implements JbigCodec {

    @Override
    public byte[] encode(Bitmap[] bitmaps) {
        if (bitmaps != null && bitmaps.length != 0) {
            return encodeNative(bitmaps);
        }

        return null;
    }

    @Override
    public Bitmap[] decode(byte[] data) {
        if (data != null && data.length != 0) {
            return decodeNative(data);
        }

        return null;
    }

    static {
        System.loadLibrary("jbigkit");
    }

    public native byte[] encodeNative(Bitmap[] bitmaps);
    public native Bitmap[] decodeNative(byte[] data);
}
