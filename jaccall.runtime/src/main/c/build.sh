#!/usr/bin/env bash
set -e;

ARCH="$(uname -m)";
BUILD_DIR="build/$ARCH";

prep_build() {
    rm -rf $BUILD_DIR;
    mkdir -p $BUILD_DIR;
}

build() {
    pushd $BUILD_DIR;
    cmake ../..
    make;
    popd;
}

pushd jaccall;
prep_build;
build;
popd;
