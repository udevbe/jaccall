package com.github.zubnix.jaccall.compiletime.linker;


import com.google.testing.compile.JavaFileObjects;

import javax.tools.JavaFileObject;

class TestCaseUtil {
    static JavaFileObject get(Class<?> testCaseClass) {
        return JavaFileObjects.forResource(testCaseClass.getResource(testCaseClass.getSimpleName() + ".java"));
    }
}
