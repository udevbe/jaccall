package com.github.zubnix.jaccall;

final class ConfigVariables {
    static final boolean JACCALL_DEBUG = Boolean.parseBoolean(System.getenv("JACCALL_DEBUG"));
    static final String JACCALL_ARCH = System.getenv("JACCALL_ARCH");
}
