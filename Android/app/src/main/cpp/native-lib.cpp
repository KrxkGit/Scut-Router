#include <jni.h>
#include <string>
#include "libSSHCommand.h"

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
Java_com_example_scut_1router_MainActivity_00024Companion_initLibSSHCommand(JNIEnv *env,
                                                                            jobject thiz) {
    Init();
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