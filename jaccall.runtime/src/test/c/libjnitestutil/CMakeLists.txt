project (jnitestutil C)

cmake_minimum_required (VERSION 2.8)

find_package(Java)
find_package(JNI REQUIRED)

include(UseJava)

if (JNI_FOUND)
    message (STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message (STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
    message (STATUS "JAVA_INCLUDE_PATH=${JAVA_INCLUDE_PATH}")
    message (STATUS "JAVA_INCLUDE_PATH2=${JAVA_INCLUDE_PATH2}")
endif()

# generate jni header with javah
add_custom_command(
        OUTPUT jni_header
        COMMAND ${Java_JAVAH_EXECUTABLE} -v
        -cp "../../../../../target/test-classes/"
        -d "../src"
        -jni com.github.zubnix.jaccall.JNITestUtil
)

# generate jnitestutil
include_directories(${JAVA_INCLUDE_PATH} ${JAVA_INCLUDE_PATH2} src)
add_library(jnitestutil MODULE src/jnitestutil jni_header)
target_link_libraries(jnitestutil)


