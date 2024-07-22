#include <jni.h>
#include <string>
#include "libSSHCommand.h"


extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_MainActivity_initLibSSHCommand(JNIEnv *env, jobject thiz) {
    Init();
}

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

    GoString usernameGo = {usernameStr, static_cast<ptrdiff_t>(strlen(usernameStr))};
    GoString passwordGo = {passwordStr, static_cast<ptrdiff_t>(strlen(passwordStr))};

    SetScutInfo(usernameGo, passwordGo);

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
Java_com_example_scut_1router_MainActivity_setAcInfo(JNIEnv *env, jobject thiz, jstring ac_name,
                                                     jstring ac_ip) {
    const char* ac_nameStr = env->GetStringUTFChars(ac_name, nullptr);
    const char* ac_ipStr = env->GetStringUTFChars(ac_ip, nullptr);

    GoString ac_nameGo = {ac_nameStr, static_cast<ptrdiff_t>(strlen(ac_nameStr))};
    GoString ac_ipGo = {ac_ipStr, static_cast<ptrdiff_t>(strlen(ac_ipStr))};

    SetAcInfo(ac_ipGo, ac_nameGo);

    env->ReleaseStringUTFChars(ac_name, ac_nameStr);
    env->ReleaseStringUTFChars(ac_ip, ac_ipStr);
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

    GoString ipGo = {ipStr, static_cast<ptrdiff_t>(strlen(ipStr))};
    GoString dns_arrGo = {dns_arrStr, static_cast<ptrdiff_t>(strlen(dns_arrStr))};
    GoString netmaskGo = {netmaskStr, static_cast<ptrdiff_t>(strlen(netmaskStr))};
    GoString gatewayGo = {gatewayStr, static_cast<ptrdiff_t>(strlen(gatewayStr))};

    SetNetwork(ipGo, dns_arrGo, netmaskGo, gatewayGo);

    env->ReleaseStringUTFChars(ip, ipStr);
    env->ReleaseStringUTFChars(dns_arr, dns_arrStr);
    env->ReleaseStringUTFChars(netmask, netmaskStr);
    env->ReleaseStringUTFChars(gateway, gatewayStr);

}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_scut_1router_ServerActivity_setAcInfo(JNIEnv *env, jobject thiz, jstring ac_ip,
                                                       jstring ac_name) {
    const char* ac_ipStr = env->GetStringUTFChars(ac_ip, nullptr);
    const char* ac_nameStr = env->GetStringUTFChars(ac_name, nullptr);

    GoString ac_ipGo = {ac_ipStr, static_cast<ptrdiff_t>(strlen(ac_ipStr))};
    GoString ac_nameGo = {ac_nameStr, static_cast<ptrdiff_t>(strlen(ac_nameStr))};

    SetAcInfo(ac_ipGo, ac_nameGo);

    env->ReleaseStringUTFChars(ac_ip, ac_ipStr);
    env->ReleaseStringUTFChars(ac_name, ac_nameStr);
}