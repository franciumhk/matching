#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_francium_1pc_matching_MainActivity_getMsg(
        JNIEnv *env,
        jobject /* this */) {
    std::string testString = "Test Message";
    return env->NewStringUTF(testString.c_str());
}
