project (JaCcall)

cmake_minimum_required (VERSION 3.3)

find_package(Java)
find_package(JNI REQUIRED)

include(UseJava)

if (JNI_FOUND)
    message (STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message (STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
    message (STATUS "JAVA_INCLUDE_PATH=${JAVA_INCLUDE_PATH}")
    message (STATUS "JAVA_INCLUDE_PATH2=${JAVA_INCLUDE_PATH2}")
endif()

add_custom_command(
        OUTPUT src/jni.h
        COMMAND ${Java_JAVAH_EXECUTABLE} -verbose
        -classpath ../../../target/classes
        -d src
        -jni com.github.zubnix.jaccall.runtime.JNI
)

# generate libjaccall
include_directories(${JAVA_INCLUDE_PATH} ${JAVA_INCLUDE_PATH2} src/main/c)
add_library(jacall MODULE src/jni.c src/jni.h)
target_link_libraries(jacall ${JNI_LIBRARIES})