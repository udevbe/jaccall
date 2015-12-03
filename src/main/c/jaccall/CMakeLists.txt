project (JaCcall C)

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

# generate jni header with javah
add_custom_command(
        OUTPUT jni_header
        COMMAND ${Java_JAVAH_EXECUTABLE} -v
        -cp "../../../../../target/classes/"
        -d "../src"
        -jni com.github.zubnix.jaccall.JNI
)

# dyncall
ADD_LIBRARY(dyncall STATIC IMPORTED)
SET_TARGET_PROPERTIES(dyncall PROPERTIES IMPORTED_LOCATION ../../dyncall/build/dyncall/libdyncall_s.a)
set(DYNCALL_INCLUDE_PATH "../dyncall/dyncall" CACHE FILEPATH "dyncall include path")
message (STATUS "DYNCALL_INCLUDE_PATH=${DYNCALL_INCLUDE_PATH}")

# dyncallback
ADD_LIBRARY(dyncallback STATIC IMPORTED)
SET_TARGET_PROPERTIES(dyncallback PROPERTIES IMPORTED_LOCATION ../../dyncall/build/dyncallback/libdyncallback_s.a)
set(DYNCALLBACK_INCLUDE_PATH "../dyncall/dyncallback" CACHE FILEPATH "dyncallback include path")
message (STATUS "DYNCALLBACK_INCLUDE_PATH=${DYNCALLBACK_INCLUDE_PATH}")

# dynload]
ADD_LIBRARY(dynload STATIC IMPORTED)
SET_TARGET_PROPERTIES(dynload PROPERTIES IMPORTED_LOCATION ../../dyncall/build/dynload/libdynload_s.a)
set(DYNLOAD_INCLUDE_PATH "../dyncall/dynload" CACHE FILEPATH "dynload include path")
message (STATUS "DYNLOAD_INCLUDE_PATH=${DYNLOAD_INCLUDE_PATH}")

# generate libjaccall
include_directories(${DYNCALL_INCLUDE_PATH} ${DYNCALLBACK_INCLUDE_PATH} ${DYNLOAD_INCLUDE_PATH} ${JAVA_INCLUDE_PATH} ${JAVA_INCLUDE_PATH2} src/main/c)
add_library(jaccall MODULE src/jni.c jni_header)
target_link_libraries(jaccall ${JNI_LIBRARIES} dyncall dyncallback dynload)
