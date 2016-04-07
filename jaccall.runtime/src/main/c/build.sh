#!/usr/bin/env bash
set -e;

#TODO add android
ARCHS=("linux-aarch64" "linux-armv7hf" "linux-armv7sf" "linux-armv6hf" "linux-x86_64" "linux-i686");

prep_build() {
    ARCH=$1
    BUILD_DIR="build/${ARCH}";

    rm -rf $BUILD_DIR;
    mkdir -p $BUILD_DIR;
}

build() {
    ARCH=$1
    BUILD_DIR="build/${ARCH}";

    pushd $BUILD_DIR;
    #TODO use docker to cross compile library based on arch & os
    cmake ../..
    make;
    popd;
}

main() {
    pushd jaccall;

    for ARCH in "${ARCHS[@]}"
    do
        prep_build "${ARCH}";
        build "${ARCH}";
    done

    popd;
}

main
