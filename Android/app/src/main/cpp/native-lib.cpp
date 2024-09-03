#include <jni.h>
#include <string>

// 检测 Android 架构
#if defined(__ANDROID__)
#if defined(__x86__)
#define ARCHITECTURE "x86"
#include "../jniLibs/x86/libSSHCommand.h"
#elif defined(__x86_64__)
#define ARCHITECTURE "x86_64"
#include "../jniLibs/x86_64/libSSHCommand.h"
#elif defined(__arm__)
#define ARCHITECTURE "ARM"
#include "../jniLibs/armeabi-v7a/libSSHCommand.h"
#elif defined(__aarch64__)
#define ARCHITECTURE "ARM64"
#include "../jniLibs/arm64-v8a/libSSHCommand.h"
#else
#define ARCHITECTURE "Unknown Architecture"
#endif
#else
#define ARCHITECTURE "Not an Android Platform"
#endif

extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_MainActivity_destroyLibSSHCommand(JNIEnv *env, jobject thiz) {
    Destroy();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_ToolsActivity_wired(JNIEnv *env, jobject thiz) {
    WiredLogin();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_ToolsActivity_wireless(JNIEnv *env, jobject thiz) {
    WirelessLogin();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_ToolsActivity_syncTime(JNIEnv *env, jobject thiz) {
    SyncTime();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_ToolsActivity_reboot(JNIEnv *env, jobject thiz) {
    Reboot();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_LoginActivity_setScutInfo(JNIEnv *env, jobject thiz, jstring username,
                                                        jstring password) {
    const char* usernameStr = env->GetStringUTFChars(username, nullptr);
    const char* passwordStr = env->GetStringUTFChars(password, nullptr);

    SetScutInfo((void *)usernameStr, (void *)passwordStr);

    env->ReleaseStringUTFChars(username, usernameStr);
    env->ReleaseStringUTFChars(password, passwordStr);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_LoginActivity_autoLogin(JNIEnv *env, jobject thiz) {
    AutoLogin();
}


extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_InternetActivity_setNetwork(JNIEnv *env, jobject thiz, jstring ip,
                                                          jstring dns_arr, jstring netmask,
                                                          jstring gateway) {

    const char* ipStr = env->GetStringUTFChars(ip, nullptr);
    const char* dns_arrStr = env->GetStringUTFChars(dns_arr, nullptr);
    const char* netmaskStr = env->GetStringUTFChars(netmask, nullptr);
    const char* gatewayStr = env->GetStringUTFChars(gateway, nullptr);

    SetNetwork((void *)ipStr, (void *)dns_arrStr, (void *)netmaskStr, (void *)gatewayStr);

    env->ReleaseStringUTFChars(ip, ipStr);
    env->ReleaseStringUTFChars(dns_arr, dns_arrStr);
    env->ReleaseStringUTFChars(netmask, netmaskStr);
    env->ReleaseStringUTFChars(gateway, gatewayStr);

}



extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_ServerActivity_setAcInfo(JNIEnv *env, jobject thiz, jstring ac_ip,
                                                       jstring ac_name) {
    const char* ac_nameStr = env->GetStringUTFChars(ac_name, nullptr);
    const char* ac_ipStr = env->GetStringUTFChars(ac_ip, nullptr);

    SetAcInfo((void *)ac_ipStr, (void *)ac_nameStr);

    env->ReleaseStringUTFChars(ac_name, ac_nameStr);
    env->ReleaseStringUTFChars(ac_ip, ac_ipStr);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_LoginActivity_cancelAutoLogin(JNIEnv *env, jobject thiz) {
    CancelAutoLogin();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_MainActivity_initLibSSHCommand(JNIEnv *env, jobject thiz,
                                                             jstring download_path) {
    const char* download_pathStr = env->GetStringUTFChars(download_path, nullptr);

    Init((void *)download_pathStr);

    env->ReleaseStringUTFChars(download_path, download_pathStr);
}