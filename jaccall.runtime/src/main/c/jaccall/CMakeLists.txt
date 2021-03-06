project (JaCcall C)

cmake_minimum_required (VERSION 2.8)
set(CMAKE_BUILD_TYPE Release)

set(CMAKE_C_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -Wall")
set(CMAKE_C_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -Wall")

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/Modules/")

find_package(Java REQUIRED)
find_package(JNI REQUIRED)
find_package(LibFFI REQUIRED)

include(UseJava)

# generate jni header with javah
add_custom_command(
        OUTPUT jni_header
        COMMAND ${Java_JAVAH_EXECUTABLE} -v
        -cp "../../../../../../target/classes/"
        -d "../../src/include"
        -jni org.freedesktop.jaccall.JNI
)

# generate libjaccall
include_directories(${JAVA_INCLUDE_PATH} ${JAVA_INCLUDE_PATH2} ${LIBFFI_INCLUDE_DIR} ${CMAKE_SOURCE_DIR}/src/include)
add_library(jaccall MODULE jni_header src/jni.c)
target_link_libraries(jaccall ${LIBFFI_LIBRARIES})
