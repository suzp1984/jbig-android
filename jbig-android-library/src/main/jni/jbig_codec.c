#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <jni.h>

#include <android/log.h>
#include <android/bitmap.h>

#include "jbig.h"

#ifndef JBIG_JNI_DEBUG
#define JBIG_JNI_DEBUG 1
#endif // JBIG_JNI_DEBUG

#define LOG_TAG "JNI_JBIG_CODEC"
#define LOGV(...) if (JBIG_JNI_DEBUG) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#define LOGI(...) if (JBIG_JNI_DEBUG) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGD(...) if (JBIG_JNI_DEBUG) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGW(...) if (JBIG_JNI_DEBUG) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...) if (JBIG_JNI_DEBUG) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

#define JBIG_MAX_RESOLUTION_WIDTH 640
#define JBIG_MAX_RESOLUTION_HEIGHT 480

#define ARGB_8888_PIXEL_ZERO 0x000000
#define RGB_565_PIXEL_ZERO 0x0000

#define ARGB_8888_PIXEL_FILL 0xff494b4b
#define RGB_565_FIXEL_FILL 0xffff

typedef struct {
    unsigned long length;
    unsigned char** buf;
} JbigData;


static void data_out(unsigned char* start, size_t len, void* buf) {
    JbigData* data = (JbigData*)buf;
    unsigned char** buffer = (unsigned char**)(data->buf);
    *buffer = (unsigned char*)realloc(*buffer, data->length + len);

    if (*buffer != NULL) {
        // LOGD("get buffer length: %d", len);
        memcpy(*buffer + data->length, start, len);
        data->length += len;
    }

    return;
}

static void* checked_malloc(size_t n) {
    void* p = NULL;

    if ((p = malloc(n)) == NULL) {
        LOGE("Sorry, not enough memory available!");
    }

    return p;
}

static void safe_free(void* buf) {
    if (buf != NULL) {
        free(buf);
        buf = NULL;
    }
}

static void handle_bit_pixel(unsigned char* bitmap, AndroidBitmapInfo info, int w, int h, int value) {
    int bpl = (info.width + 7) / 8;
    int offset = (bpl * h) + (w/8);

    uint8_t* v = bitmap + offset;

    if (value == 0) {

    } else { // 1
        *v = (*v) | (uint8_t)(1 << (7 - (w % 8)));
    }
}

static void handle_rgb565_pixels(unsigned char* bitmap, AndroidBitmapInfo info, void* data)
{
    LOGD("####  handle rgb 565 pixels ###");
    LOGD("AndridBitmapInfo.stride is %d", info.stride);
    int bpl = (info.width + 7) / 8;
    uint16_t* pixels = (uint16_t*) data;

    int w = info.width;
    int h = info.height;
    int j = 0;
    int i = 0;

    for (j = 0; j < h; j++) {
        uint16_t* line = (uint16_t*) pixels;

        for (i = 0; i < w; i++) {
            uint16_t p = line[i];
            if (p == RGB_565_PIXEL_ZERO) {
                handle_bit_pixel(bitmap, info, i, j, 0);
            } else { // 1
                handle_bit_pixel(bitmap, info, i, j, 1);
            }
        }

        pixels = pixels + info.stride;
    }
}

static void handle_rgba8888_pixels(unsigned char* bitmap, AndroidBitmapInfo info, void* data)
{
    LOGD("####  handle rgba 8888 pixels ###");
    LOGD("AndridBitmapInfo.stride is %d", info.stride);
    int bpl = (info.width + 7) / 8; // bytes per line
    uint32_t* pixels = (uint32_t*)data;

    int w = info.width;
    int h = info.height;
    int i = 0;
    int j = 0;

    for(j = 0; j < h; j++) {
    uint32_t* line = (uint32_t*)pixels;

        for (i = 0; i < w; i++) {
            uint32_t p = line[i];
            if (p == ARGB_8888_PIXEL_ZERO) {
                handle_bit_pixel(bitmap, info, i, j, 0);
            } else { // 1
                // LOGE("the color is 0x%08x", p);
                handle_bit_pixel(bitmap, info, i, j, 1);
            }
        }

        pixels = (uint32_t*) ((char*)pixels + info.stride);
    }

}

static void handle_pixels(unsigned char* bitmap, AndroidBitmapInfo info, void* pixels)
{
    if (info.format == ANDROID_BITMAP_FORMAT_RGB_565) {
        handle_rgb565_pixels(bitmap, info, pixels);
    } else if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        handle_rgba8888_pixels(bitmap, info, pixels);
    } else {
        LOGE("Error! unsupported Bitmap pixels format. must be rgba_8888 or rgb_565.");
    }
}

static jobject decode_jbig_plane(JNIEnv* env, unsigned char* p, unsigned long size, int w, int h)
{
    jobject bitmap;
    jintArray argb8888_pixels_jarray;

    jclass bmpCfgCls = (*env)->FindClass(env, "android/graphics/Bitmap$Config");
    jmethodID bmpCfgClsValueOf = (*env)->GetStaticMethodID(env, bmpCfgCls, "valueOf",
                                    "(Ljava/lang/String;)Landroid/graphics/Bitmap$Config;");
    jobject jBmpCfg = (*env)->CallStaticObjectMethod(env, bmpCfgCls, bmpCfgClsValueOf,
                                (*env)->NewStringUTF(env, "ARGB_8888"));

    jclass bmpCls = (*env)->FindClass(env, "android/graphics/Bitmap");
    jmethodID createBmpMd = (*env)->GetStaticMethodID(env, bmpCls, "createBitmap",
                        "([IIILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;");

    int argb8888_pixels_size = w * h;
    uint32_t* argb8888_pixels = (uint32_t*) checked_malloc(sizeof(uint32_t) * argb8888_pixels_size);
    unsigned char* bit_pixel_buf = p;
    int i = 0;
    int j = 0;
    int bpl = (w + 7) / 8; // bytes per line

    for (j = 0; j < h; j++) {
        for (i = 0; i < w; i++) {
            unsigned char* bit_p = bit_pixel_buf + bpl * j + i / 8;
            uint32_t* argb8888_p = argb8888_pixels + w * j + i;
            int offset = 7 - (i % 8);

            if ((*bit_p & (1 << offset)) == 0) {
                *argb8888_p = ARGB_8888_PIXEL_ZERO;
            } else {
                *argb8888_p = ARGB_8888_PIXEL_FILL;
            }
        }
    }

    argb8888_pixels_jarray = (*env)->NewIntArray(env, argb8888_pixels_size);
    (*env)->SetIntArrayRegion(env, argb8888_pixels_jarray, 0, argb8888_pixels_size, argb8888_pixels);
    bitmap = (*env)->CallStaticObjectMethod(env, bmpCls, createBmpMd, argb8888_pixels_jarray, w, h, jBmpCfg);

    safe_free(argb8888_pixels);

    return bitmap;
}

jbyteArray Java_io_github_suzp1984_jbig_JniJbigCodec_encodeNative(JNIEnv* env, jobject thiz, jobjectArray bitmap_array)
{
    LOGI("jbig encode native");

    int i = 0;
    int ret = 0;
    int encode_planes = -1;
    int width = 0;
    int height = 0;
    int bpl = 0;

    unsigned char** bitmaps;
    unsigned char* res_buf = NULL; // result buffer
    JbigData data = {0, &res_buf};
    struct jbg_enc_state s;

    int options = JBG_TPDON | JBG_TPBON | JBG_DPON;
    int order = JBG_ILEAVE | JBG_SMID;
    data.length = 0;

    // prepare layer width, height, encode_planes and bitmaps here
    encode_planes = (*env)->GetArrayLength(env, bitmap_array);
    LOGV("**** number of planes = %d", encode_planes);
    bitmaps = (unsigned char**) checked_malloc(sizeof(unsigned char*) * encode_planes);

    for(i = 0; i < encode_planes; i++) {
        jobject bitmap = (*env)->GetObjectArrayElement(env, bitmap_array, i);
        AndroidBitmapInfo info;
        void* pixels;
        if ((ret = AndroidBitmap_getInfo(env, bitmap, &info)) < 0) {
            LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
            return NULL;
        }

        if (i == 0) {
            width = info.width;
            height = info.height;
            bpl = (width + 7) / 8; // bytes per line
        }

        LOGV("Bitmap width = %d", width);
        LOGV("Bitmap height = %d", height);
        LOGV("bytes per line is %d", bpl);

        int bitmap_size = bpl * height;
        LOGV("bitmap size is %d", bitmap_size);

        bitmaps[i] = (unsigned char*) checked_malloc(bitmap_size);
        memset(bitmaps[i], 0, bitmap_size);

        if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixels)) < 0) {
            LOGE("AndroidBitmap_lockPixels() failed ! error = %d", ret);
        }
        // Handle pixels here.
        handle_pixels(bitmaps[i], info, pixels);

        AndroidBitmap_unlockPixels(env, bitmap);
    }

    jbg_enc_init(&s, width, height, encode_planes, bitmaps, data_out, (void*)&data);
    // the first layer was the default lower resolution layer, maybe use jbg_enc_lrlmax instead
    // jbg_enc_layers(&s, 0);
    jbg_enc_lrlmax(&s, JBIG_MAX_RESOLUTION_WIDTH, JBIG_MAX_RESOLUTION_HEIGHT);
    // should I add trading no into comment
    jbg_enc_lrange(&s, -1, -1);
    jbg_enc_options(&s, order, options, 0, -1, -1);

    jbg_enc_out(&s);
    jbg_enc_free(&s);

    jbyteArray ret_array = (*env)->NewByteArray(env, data.length);
    LOGV("jbig data lenght = %lu", data.length);
    (*env)->SetByteArrayRegion(env, ret_array, 0, data.length, (jbyte*)res_buf);
    safe_free(res_buf);

    for (i = 0; i < encode_planes; i++) {
        safe_free(bitmaps[i]);
    }

    safe_free(bitmaps);

    return ret_array;
}

jobjectArray Java_io_github_suzp1984_jbig_JniJbigCodec_decodeNative(JNIEnv* env, jobject thiz, jbyteArray data)
{
    LOGI("jbig decode native");
    struct jbg_dec_state s;
    unsigned char* buffer;
    unsigned char* p;
    uint32_t buf_len = 0;
    int result;
    size_t cnt;
    size_t bytes_read = 0;
    int planes;
    int i = 0;
    int w = 0;
    int h = 0;
    unsigned long plane_size;
    unsigned long xmax = 4294967295UL;
    unsigned long ymax = 4294967295UL;

    jobjectArray bitmaps_array;
    jclass bitmapCls = (*env)->FindClass(env, "android/graphics/Bitmap");

    buffer = (*env)->GetByteArrayElements(env, data, NULL);
    buf_len = (*env)->GetArrayLength(env, data);

    jbg_dec_init(&s);
    jbg_dec_maxsize(&s, xmax, ymax);

    if (buf_len < 20) {
        LOGE("Jbig byte array must be at least 20 bytes long.");
        return NULL;
    }

    if (buffer[19] & JBG_VLENGTH) {
        result = jbg_newlen(buffer, buf_len);
        if (result == JBG_EOK) {
            LOGD("---jbig decodeNative, runing decode here!");
            p = (unsigned char*) buffer;
            result = JBG_EAGAIN;
            while (buf_len > 0 &&
                    result == JBG_EAGAIN) {
                result = jbg_dec_in(&s, p, buf_len, &cnt);
                p += cnt;
                buf_len -= cnt;
                bytes_read += cnt;
            }
        }
    } else {
        LOGD("jbig decodeNative. buffer len = %d", buf_len);
        result = JBG_EAGAIN;
        cnt = 0;
        p = (unsigned char*) buffer;
        while (buf_len > 0 && result == JBG_EAGAIN) {
            result = jbg_dec_in(&s, p, buf_len, &cnt);
            p += cnt;
            buf_len -= cnt;
            bytes_read += cnt;
            LOGD("buf_len = %d", buf_len);
        }
    }

    if (result != JBG_EOK && result != JBG_EOK_INTR) {
        LOGE("Problem with jbig data, : %s (error code 0x%02x, %lu = 0x%04lx BIE bytes processed)",
             jbg_strerror(result), result, (unsigned long)bytes_read, (unsigned long)bytes_read);
        return NULL;
    }

    planes = jbg_dec_getplanes(&s);
    if (planes <= 0) {
        LOGE("jbig decode get zero planes.");
        return NULL;
    }

    // create bitmaps_array here
    bitmaps_array = (*env)->NewObjectArray(env, planes, bitmapCls, NULL);

    w = jbg_dec_getwidth(&s);
    h = jbg_dec_getheight(&s);
    plane_size = jbg_dec_getsize(&s);
    LOGD("jbig decode get plane width = %d, height = %d, plane bytes size = %lu", w, h, plane_size);

    for (i = 0; i < planes; i++) {
        p = jbg_dec_getimage(&s, i);  // pointer to the beginning of the buffer

        (*env)->SetObjectArrayElement(env, bitmaps_array, i, decode_jbig_plane(env, p, plane_size, w, h));
    }

    jbg_dec_free(&s);

    return bitmaps_array;
}