#!/usr/bin/env bash
set -e;

prep_build() {
    rm -rf build/;
    mkdir -p build;
}

build() {
    pushd build;
    cmake ..;
    make;
    popd;
}

pushd jaccall;
prep_build;
build;
popd;
