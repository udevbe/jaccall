#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "testing.h"

struct test structTest(struct test *tst,
                       char field0,
                       short field1,
                       int field2[3],
                       int *field3,
                       long long embedded_field0,
                       float embedded_field1) {

    tst->field0 = field0;
    tst->field1 = field1;
    tst->field2[0] = field2[0];
    tst->field2[1] = field2[1];
    tst->field2[2] = field2[2];
    tst->field3 = field3;
    tst->field4.embedded_field0 = embedded_field0;
    tst->field4.embedded_field1 = embedded_field1;

    struct test some_test = {
            .field0 = tst->field0,
            .field1 = tst->field1,
            .field2[0] = tst->field2[0],
            .field2[1] = tst->field2[1],
            .field2[2] = tst->field2[2],
            .field3 = tst->field3,
            .field4.embedded_field0 = tst->field4.embedded_field0,
            .field4.embedded_field1 = tst->field4.embedded_field1
    };

    return some_test;
}

struct test *structTest2(struct test tst,
                         char field0,
                         short field1,
                         int field2[3],
                         int *field3,
                         long long embedded_field0,
                         float embedded_field1) {

    struct test *some_test = malloc(sizeof(struct test));

    tst.field0 = field0;
    tst.field1 = field1;
    tst.field2[0] = field2[0];
    tst.field2[1] = field2[1];
    tst.field2[2] = field2[2];
    tst.field3 = field3;
    tst.field4.embedded_field0 = embedded_field0;
    tst.field4.embedded_field1 = embedded_field1;

    some_test->field0 = tst.field0;
    some_test->field1 = tst.field1;
    some_test->field2[0] = tst.field2[0];
    some_test->field2[1] = tst.field2[1];
    some_test->field2[2] = tst.field2[2];
    some_test->field3 = tst.field3;
    some_test->field4.embedded_field0 = tst.field4.embedded_field0;
    some_test->field4.embedded_field1 = tst.field4.embedded_field1;

    return some_test;
}

union testunion unionTest(union testunion *tst,
                          int field0,
                          float field1) {
    tst->field0 = field0;

    union testunion some_test;
    some_test.field1 = field1;

    return some_test;

}

union testunion *unionTest2(union testunion tst,
                            int field0) {
    tst.field0 = field0;

    union testunion *some_test = malloc(sizeof(union testunion));
    some_test->field1 = tst.field1;
    return some_test;
}

char charTest(char value) {
    return value;
}

unsigned char unsignedCharTest(unsigned char value) {
    return value;

}

short shortTest(short value) {
    return value;

}

unsigned short unsignedShortTest(unsigned short value) {
    return value;

}

int intTest(int value) {
    return value;

}

unsigned int unsignedIntTest(unsigned int value) {
    return value;

}

long longTest(long value) {
    return value;

}

unsigned long unsignedLongTest(unsigned long value) {
    return value;

}

long long longLongTest(long long value) {
    return value;

}

unsigned long long unsignedLongLongTest(unsigned long long value) {
    return value;

}

float floatTest(float value) {
    return value;

}

double doubleTest(double value) {
    return value;

}

void *pointerTest(void *value) {
    return value;

}

void noArgsTest(void) {
    //do nothing but have something so we 're included as a symbol
    struct test testStruct;
}

funcptr noArgsFuncPtrTest(void) {
    return &noArgsTest;
}

char function(struct test* testStructByRef, unsigned int arg1, struct test testStructByVal){
        testStructByRef->field2[0] = arg1;
        testStructByRef->field2[1] = (unsigned int) testStructByVal.field1;
        testStructByRef->field3 = testStructByVal.field3;

        return testStructByVal.field0;
}

testFunc getFunctionPointerTest(){
    return &function;
}


char functionPointerTest(testFunc func, struct test* arg0, unsigned int arg1, struct test arg2){
    return func(arg0,arg1,arg2);
}

char (*charTestFunctionPointer(void))(char){
    return &charTest;
}

unsigned char (*unsignedCharTestFunctionPointer(void))(unsigned char){
    return &unsignedCharTest;
}

short (*shortTestFunctionPointer(void))(short){
   return &shortTest;
}

unsigned short (*unsignedShortTestFunctionPointer(void))(unsigned short){
   return &unsignedShortTest;
}

int (*intTestFunctionPointer(void))(int){
   return &intTest;
}

unsigned int (*unsignedIntTestFunctionPointer(void))(unsigned int){
   return &unsignedIntTest;
}

long (*longTestFunctionPointer(void))(long){
   return &longTest;
}

unsigned long (*unsignedLongTestFunctionPointer(void))(unsigned long){
   return &unsignedLongTest;
}

long long (*longLongTestFunctionPointer(void))(long long){
   return &longLongTest;
}

unsigned long long (*unsignedLongLongTestFunctionPointer(void))(unsigned long long){
   return &unsignedLongLongTest;
}

float (*floatTestFunctionPointer(void))(float){
    return &floatTest;
}

double (*doubleTestFunctionPointer(void))(double){
    return &doubleTest;
}

void* (*pointerTestFunctionPointer(void))(void*){
    return &pointerTest;
}

struct test (*structTestFunctionPointer(void))(struct test *tst,
                                               char field0,
                                               short field1,
                                               int field2[3],
                                               int *field3,
                                               long long embedded_field0,
                                               float embedded_field1){
    return &structTest;
}

struct test* (*structTest2FunctionPointer(void))(struct test tst,
                                                 char field0,
                                                 short field1,
                                                 int field2[3],
                                                 int *field3,
                                                 long long embedded_field0,
                                                 float embedded_field1){
     return &structTest2;
}

union testunion (*unionTestFunctionPointer(void))(union testunion *tst,
                                                  int embedded_field0,
                                                  float embedded_field1){
    return &unionTest;
}

union testunion* (*unionTest2FunctionPointer(void))(union testunion tst,
                                                    int embedded_field0){
    return &unionTest2;
}

void writeFieldsTestStruct(struct fieldsTest *fTest,
                           char charField,
                           short shortField,
                           int intField,
                           long longField,
                           long long longLongField,
                           float floatField,
                           double doubleField,
                           void* pointerField,
                           void** pointerArrayField,
                           struct test_embedded structField,
                           struct test_embedded structArrayField[],
                           int structArrayFieldSize){
    fTest->charField = charField;
    fTest->shortField = shortField;
    fTest->intField = intField;
    fTest->longField = longField;
    fTest->longLongField = longLongField;
    fTest->floatField = floatField;
    fTest->doubleField = doubleField;
    fTest->pointerField = pointerField;
    fTest->pointerArrayField[0] = pointerArrayField[0];
    fTest->pointerArrayField[1] = pointerArrayField[1];
    fTest->pointerArrayField[2] = pointerArrayField[2];
    fTest->structField = structField;
    memcpy(&fTest->structArrayField, &structArrayField[0], structArrayFieldSize * sizeof(struct test_embedded));
}

int readGlobalVar(void){
    return globalvar;
}

void writeGlobalVar(int var){
    globalvar = var;
}