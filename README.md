# jbig-android
This is a [JBIG](https://en.wikipedia.org/wiki/JBIG) codec toolkit in Android platform.

# implementation detail

jbig-android is a android level wrapper above the [JBIG-KIT](http://www.cl.cam.ac.uk/~mgk25/jbigkit/).
So it include [JBIG-KIT](http://www.cl.cam.ac.uk/~mgk25/jbigkit/)'s source code inside of
it in Android's JNI level.

# API document

```java
 # encode code
    Bitmap[] bitmaps = ...;

    JbigCodec jbigCodec = JbigCodecFactory.getJbigCodec(JbigCodecFactory.CODEC.JNI_CODEC);
    byte[] jbigData = jbigCodec.encode(bitmaps);

```

Above code is what I said jbig encoding from multiple layers of Bitmaps.


```java
    # jbigData is the above byte[] jbigData.

    JbigCodec jbigCodec = JbigCodecFactory.getJbigCodec(JbigCodecFactory.CODEC.JNI_CODEC);
    Bitmap[] bms = jbigCodec.decode(jbigData);

```

And this is the decoder part, the reverting process of above encoding part, here it
decode the jbigData byte array into multiple layers of Bitmaps.

# Sample app



