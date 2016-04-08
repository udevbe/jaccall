#!/usr/bin/env bash
set -e;

DOCKER_IMAGE="zubnix/jni-cross-compilers";
#ARCHS match docker tags of "zubnix/jni-cross-compilers" images
ARCHS=("linux-aarch64" "linux-armv7hf" "linux-armv7sf" "linux-armv6hf" "linux-x86_64" "linux-i686");

#TODO add android support

prep_build_for_arch() {
    ARCH=$1
    BUILD_DIR="build/${ARCH}";

    rm -rf $BUILD_DIR;
    mkdir -p $BUILD_DIR;
}

build_for_arch() {
    ARCH=$1
    BUILD_DIR="build/${ARCH}";

    pushd $BUILD_DIR;
    #TODO use docker to cross compile library based on arch & os
    cmake ../..;
    make;
    popd;
}

cross_compile_all() {
    for ARCH in "${ARCHS[@]}"
    do
        prep_build_for_arch "${ARCH}";
        build_for_arch "${ARCH}";
    done
}

native_compile() {
    ARCH="native";
    prep_build_for_arch "${ARCH}";

    BUILD_DIR="build/${ARCH}";
    pushd $BUILD_DIR;
        cmake ../..;
        make;
    popd;
}

main() {
    pushd jaccall;
        #if cmake is not installed, bail out.
        command -v cmake >/dev/null 2>&1 || { echo >&2 "cmake is required but it's not installed.  Aborting."; exit 1; }

        #if docker is not installed, do a plain native compilation
        command -v docker >/dev/null 2>&1 || { native_compile; exit 0; }

        #else cross compile all archs using docker
        #TODO perhaps we want a command line switch to trigger cross compilation(?)
        cross_compile_all;
    popd;
}

main
