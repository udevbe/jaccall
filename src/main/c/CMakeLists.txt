project (JaCcall)

cmake_minimum_required (VERSION 3.3)

find_package(JNI REQUIRED)

if (JNI_FOUND)
    message (STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message (STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
    message (STATUS "JAVA_INCLUDE_PATH=${JAVA_INCLUDE_PATH}")
    message (STATUS "JAVA_INCLUDE_PATH2=${JAVA_INCLUDE_PATH2}")
endif()

# generate JNIFoo.h stub
set (_stubDir "${CMAKE_CURRENT_BINARY_DIR}")
add_custom_command(
        OUTPUT jni.h
        COMMAND ${Java_JAVAH_EXECUTABLE} -verbose
        -classpath ../../../target/classes
        -jni com.github.zubnix.JNI
)

# generate libjaccall
include_directories(${JAVA_INCLUDE_PATH} ${JAVA_INCLUDE_PATH2} src/main/c)
add_library(jacall MODULE jni.h jni.c)
target_link_libraries(jacall ${JNI_LIBRARIES})