project (JaCcall C)

cmake_minimum_required (VERSION 3.3)

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "${CMAKE_SOURCE_DIR}/cmake/Modules/")

find_package(Java REQUIRED)
find_package(JNI REQUIRED)
find_package(LibDL REQUIRED)
find_package(LibFFI REQUIRED)

include(UseJava)

# generate jni header with javah
add_custom_command(
        OUTPUT jni_header
        COMMAND ${Java_JAVAH_EXECUTABLE} -v
        -cp "../../../../../target/classes/"
        -d "../src/include"
        -jni com.github.zubnix.jaccall.JNI
)

# generate libjaccall
include_directories(${JAVA_INCLUDE_PATH} ${JAVA_INCLUDE_PATH2} ${LIBFFI_INCLUDE_DIR} ${LIBDL_INCLUDE_DIR} ${CMAKE_SOURCE_DIR}/src/include)
add_library(jaccall MODULE jni_header src/jni.c)
target_link_libraries(jaccall ${JNI_LIBRARIES} ${LIBFFI_LIBRARIES} ${LIBDL_LIBRARIES})
