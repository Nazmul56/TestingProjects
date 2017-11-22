/*
 * Copyright (C) 2011 Kyan He <kyan.ql.he@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <jni.h>

#include <math.h>
#include <string.h>
#include "iLBC_define.h"
#include "iLBC_decode.h"
#include "iLBC_encode.h"

#define LOG_TAG "ilbc-codec"

#ifdef BUILD_FROM_SOURCE
#include <utils/Log.h>
#else
#include <android/log.h>
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__) 
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG  , LOG_TAG, __VA_ARGS__) 
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO   , LOG_TAG, __VA_ARGS__) 
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN   , LOG_TAG, __VA_ARGS__) 
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR  , LOG_TAG, __VA_ARGS__) 

#endif

#define JNI_COPY    0

static iLBC_Enc_Inst_t g_enc_inst;
static iLBC_Dec_Inst_t g_dec_inst;

static int encode(short *samples, unsigned char *data)
{
    int i;
    float block[BLOCKL_MAX];

    // Convert to float representaion of voice signal.
    for (i = 0; i < g_enc_inst.blockl; i++) {
        block[i] = samples[i];
    }

    iLBC_encode(data, block, &g_enc_inst);

    return g_enc_inst.no_of_bytes;
}

static int decode(unsigned char *data, short *samples, int mode)
{
    int i;
    float block[BLOCKL_MAX];

    // Validate Mode
    if (mode != 0 && mode != 1) {
        LOGE("Bad mode");
        return -1;
    }

    iLBC_decode(block, data, &g_dec_inst, mode);

    // Validate PCM16
    for (i = 0; i < g_dec_inst.blockl; i++) {
        float point;

        point = block[i];
        if (point < MIN_SAMPLE) {
            point = MIN_SAMPLE;
        } else if (point > MAX_SAMPLE) {
            point = MAX_SAMPLE;
        }

        samples[i] = point;
    }

    return g_dec_inst.blockl * 2;
}

jint Java_com_googlecode_androidilbc_Codec_init(
        JNIEnv *env, jobject this, jint mode)
{
    initEncode(&g_enc_inst, mode);
    initDecode(&g_dec_inst, mode, 1);
}

/**
 * TODO: API of mode.
 *
 */
jint Java_com_googlecode_androidilbc_Codec_encode(
        JNIEnv *env, jobject this,
        jbyteArray sampleArray, jint sampleOffset, jint sampleLength,
        jbyteArray dataArray, jint dataOffset)
{
    jsize samples_sz, data_sz;
    jbyte *samples, *data;
    int bytes_to_encode;
    int bytes_encoded;

    LOGD("encode(%p, %d, %d, %p, %d, %d)",
            sampleArray, sampleOffset, sampleLength,
            dataArray, dataOffset);

    samples_sz = (*env)->GetArrayLength(env, sampleArray);
    samples = (*env)->GetByteArrayElements(env, sampleArray, JNI_COPY);
    data_sz= (*env)->GetArrayLength(env, dataArray);
    data = (*env)->GetByteArrayElements(env, dataArray, JNI_COPY);

    samples += sampleOffset;
    data += dataOffset;

    bytes_to_encode = sampleLength;
    bytes_encoded = 0;

    int truncated = bytes_to_encode % (g_enc_inst.blockl * 2);
    if (!truncated) {
        LOGW("Ignore last %d bytes", truncated);
        bytes_to_encode -= truncated;
    }

    while (bytes_to_encode > 0) {

        int _encoded;

        _encoded = encode((short *)samples, data);

        samples += g_enc_inst.blockl * 2;
        data += _encoded;

        bytes_encoded += _encoded;
        bytes_to_encode -= g_enc_inst.blockl * 2;
    }

    // Revert buffer pointers
    samples -= sampleLength;
    data -= bytes_encoded;

    (*env)->ReleaseByteArrayElements(env, sampleArray, samples, JNI_COPY);
    (*env)->ReleaseByteArrayElements(env, dataArray, data, JNI_COPY);

    return bytes_encoded;
}

/**
 * TODO: API of mode.
 *
 */
jint Java_com_googlecode_androidilbc_Codec_decode(
        JNIEnv *env, jobject this,
        jbyteArray dataArray, jint dataOffset, jint dataLength,
        jbyteArray sampleArray, jint sampleOffset)
{
    jsize samples_sz, data_sz;
    jbyte *samples, *data;
    int bytes_to_decode, bytes_decoded;

    samples_sz = (*env)->GetArrayLength(env, sampleArray);
    samples = (*env)->GetByteArrayElements(env, sampleArray, JNI_COPY);
    data_sz= (*env)->GetArrayLength(env, dataArray);
    data = (*env)->GetByteArrayElements(env, dataArray, JNI_COPY);

    samples += sampleOffset;
    data += dataOffset;

    bytes_to_decode = dataLength;
    bytes_decoded = 0;

    while (bytes_to_decode > 0) {

        int _decoded;

        _decoded = decode(data, (short *)samples, 1);

        samples += _decoded;
        data += g_dec_inst.no_of_bytes;

        bytes_decoded += _decoded;
        bytes_to_decode -= g_dec_inst.no_of_bytes;
    }

    // Revert buffer pointers
    samples -= bytes_decoded;
    data -= dataLength;

    (*env)->ReleaseByteArrayElements(env, sampleArray, samples, JNI_COPY);
    (*env)->ReleaseByteArrayElements(env, dataArray, data, JNI_COPY);

    return bytes_decoded;
}
