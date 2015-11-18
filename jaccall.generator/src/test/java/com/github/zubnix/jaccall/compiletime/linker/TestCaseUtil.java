package com.github.zubnix.jaccall.compiletime.linker;


import com.google.testing.compile.JavaFileObjects;

import javax.tools.JavaFileObject;

class TestCaseUtil {
    static JavaFileObject get(Class<?> testCaseClass) {
        //java test case source files are copied to the test classes compilation output by a maven plugin
        return JavaFileObjects.forResource(testCaseClass.getResource(testCaseClass.getSimpleName() + ".java"));
    }
}
