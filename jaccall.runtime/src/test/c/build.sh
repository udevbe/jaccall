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

pushd libjnitestutil
prep_build;
build;
popd;
popd;

pushd libtesting;
prep_build;
build;
popd;
popd;
