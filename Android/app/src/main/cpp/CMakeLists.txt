# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name. The project name can be accessed via ${ PROJECT_NAME},
# Since this is the top level CMakeLists.txt, the project name is also accessible
# with ${CMAKE_PROJECT_NAME} (both CMake variables are in-sync within the top level
# build script scope).
project("scut_router")

# 输出正在编译的架构
message(STATUS "ANDROID_ABI: ${ANDROID_ABI}")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
#
# In this top level CMakeLists.txt, ${CMAKE_PROJECT_NAME} is used to define
# the target library name; in the sub-module's CMakeLists.txt, ${PROJECT_NAME}
# is preferred for the same purpose.
#
# In order to load a library into your app from Java/Kotlin, you must call
# System.loadLibrary() and pass the name of the library defined here;
# for GameActivity/NativeActivity derived applications, the same library name must be
# used in the AndroidManifest.xml file.
add_library(${CMAKE_PROJECT_NAME} SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        native-lib.cpp)


# 注意加上 ${CMAKE_SOURCE_DIR} 环境变量，否则会找不到头文件
set(HEADER_PATH ${CMAKE_SOURCE_DIR}/../../../../../Compatibility/out/)

# 将库添加到 jniLibs 目录下，保证会自动拷贝到 apk 包
set(Compatibility_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}) # 跨平台编译
add_library(Compatibility SHARED IMPORTED)
set_property(TARGET Compatibility PROPERTY IMPORTED_NO_SONAME 1)
set_target_properties(Compatibility PROPERTIES IMPORTED_LOCATION ${Compatibility_DIR}/libSSHCommand.so)
include_directories(${Compatibility_DIR})


# Specifies libraries CMake should link to your target library. You
# can link libraries from various origins, such as libraries defined in this
# build script, prebuilt third-party libraries, or Android system libraries.
target_link_libraries(${CMAKE_PROJECT_NAME}
        # List libraries link to the target library
        Compatibility
        android
        log)