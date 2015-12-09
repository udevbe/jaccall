project (JaCcall C)

cmake_minimum_required (VERSION 3.3)

find_package(Java)
find_package(JNI REQUIRED)

include(UseJava)

# generate jni header with javah
add_custom_command(
        OUTPUT jni_header
        COMMAND ${Java_JAVAH_EXECUTABLE} -v
        -cp "../../../../../target/classes/"
        -d "../src"
        -jni com.github.zubnix.jaccall.JNI
)

# generate libjaccall
include_directories(${JAVA_INCLUDE_PATH} ${JAVA_INCLUDE_PATH2} src/main/c)
add_library(libdl SHARED IMPORTED GLOBAL)
add_library(libffi SHARED IMPORTED GLOBAL)
add_library(jaccall MODULE src/jni.c jni_header)
target_link_libraries(jaccall ${JNI_LIBRARIES} ffi dl)
