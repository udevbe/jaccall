#!/usr/bin/env bash
set -e;

BUILD_ROOT="src/main/c/jaccall/build";
DOCKER_IMAGE="zubnix/jni-cross-compilers";
#ARCHS match docker tags of "zubnix/jni-cross-compilers" images
ARCHS=("linux-aarch64" "linux-armv7hf" "linux-armv7sf" "linux-armv6hf" "linux-x86_64" "linux-i686");

#TODO add android support

prep_build_for_arch() {
    ARCH=$1
    BUILD_DIR="${BUILD_ROOT}/${ARCH}";

    rm -rf ${BUILD_DIR};
    mkdir -p ${BUILD_DIR};
}

build_for_arch() {
    ARCH=$1
    BUILD_DIR="${BUILD_ROOT}/${ARCH}";

    USER_IDS="-e BUILDER_UID=$( id -u ) -e BUILDER_GID=$( id -g )"
    docker run --rm -v $PWD:/build ${USER_IDS} ${DOCKER_IMAGE}:${ARCH} "cmake" "-DCMAKE_TOOLCHAIN_FILE=\$CMAKE_TOOLCHAIN_FILE" "-H${BUILD_ROOT}/.." "-B${BUILD_DIR}"
    docker run --rm -v $PWD:/build ${USER_IDS} ${DOCKER_IMAGE}:${ARCH} "make" "-C" "${BUILD_DIR}"
}

cross_compile_all() {
    for ARCH in "${ARCHS[@]}"
    do
        prep_build_for_arch "${ARCH}";
        build_for_arch "${ARCH}";
    done
}

native_compile() {
    #if cmake is not installed, bail out.
    command -v cmake >/dev/null 2>&1 || { echo >&2 "cmake is required but it's not installed.  Aborting."; exit 1; }

    ARCH="native";
    prep_build_for_arch "${ARCH}";

    BUILD_DIR="${BUILD_ROOT}/${ARCH}";
    pushd ${BUILD_DIR};
        cmake ../..;
        make;
    popd;
}

main() {
    #if docker is not installed, do a plain native compilation
    command -v docker >/dev/null 2>&1 || { native_compile; exit 0; }
    #else cross compile all archs using docker
    #TODO perhaps we want a command line switch to trigger cross compilation(?)
    cross_compile_all;
}

main
