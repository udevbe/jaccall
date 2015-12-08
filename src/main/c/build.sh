#!/usr/bin/env bash

prep_build() {
    mkdir -p build;
    pushd build;
    rm -rf *;
}

build() {
    cmake ..
    make
}

pushd jaccall;
prep_build;
build;
popd;
popd;
