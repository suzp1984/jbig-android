
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := jbigkit
LOCAL_SRC_FILES :=  jbig_codec.c \
					jbig.c \
					jbig_ar.c \

LOCAL_C_INCLUDES += \
    $(JNI_H_INCLUDE) \
					
LOCAL_SHARED_LIBRARIES := \
    libandroid_runtime \
    libnativehelper \
    libcutils \
    libutils \
    liblog \
    libhardware

LOCAL_LDLIBS := -llog -ljnigraphics

include $(BUILD_SHARED_LIBRARY)
