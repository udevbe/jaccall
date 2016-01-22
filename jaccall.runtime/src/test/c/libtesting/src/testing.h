struct test {
    char field0;
    short field1;
    int field2[3];
    int *field3;
    struct test_embedded {
        long long embedded_field0;
        float embedded_field1;
    } field4;
};

union testunion {
    int field0;
    float field1;
};

typedef char(*testFunc)(struct test*, unsigned int, struct test);

char function(struct test* arg0, unsigned int arg1, struct test arg2);

testFunc getFunctionPointerTest();

char functionPointerTest(testFunc function, struct test* arg0, unsigned int arg1, struct test arg2);

struct test structTest(struct test *tst,
                       char field0,
                       short field1,
                       int field2[3],
                       int *field3,
                       long long embedded_field0,
                       float embedded_field1);

struct test *structTest2(struct test tst,
                         char field0,
                         short field1,
                         int field2[3],
                         int *field3,
                         long long embedded_field0,
                         float embedded_field1);

union testunion unionTest(union testunion *tst,
                          int field0,
                          float field1);

union testunion *unionTest2(union testunion tst,
                            int field0);

char charTest(char value);

unsigned char unsignedCharTest(unsigned char value);

short shortTest(short value);

unsigned short unsignedShortTest(unsigned short value);

int intTest(int value);

unsigned int unsignedIntTest(unsigned int value);

long longTest(long value);

unsigned long unsignedLongTest(unsigned long value);

long long longLongTest(long long value);

unsigned long long unsignedLongLongTest(unsigned long long value);

float floatTest(float value);

double doubleTest(double value);

void *pointerTest(void *value);

void noArgsTest(void);

typedef void(*funcptr)(void);

funcptr noArgsFuncPtrTest(void);

char (*charTestFunctionPointer(void))(char);

unsigned char (*unsignedCharTestFunctionPointer(void))(unsigned char);

short (*shortTestFunctionPointer(void))(short);

unsigned short (*unsignedShortTestFunctionPointer(void))(unsigned short);

int (*intTestFunctionPointer(void))(int);

unsigned int (*unsignedIntTestFunctionPointer(void))(unsigned int);

long (*longTestFunctionPointer(void))(long);

unsigned long (*unsignedLongTestFunctionPointer(void))(unsigned long);

long long (*longLongTestFunctionPointer(void))(long long);

unsigned long long (*unsignedLongLongTestFunctionPointer(void))(unsigned long long);

float (*floatTestFunctionPointer(void))(float);

double (*doubleTestFunctionPointer(void))(double);

void* (*pointerTestFunctionPointer(void))(void*);

struct test (*structTestFunctionPointer(void))(struct test *tst,
                                               char field0,
                                               short field1,
                                               int field2[3],
                                               int *field3,
                                               long long embedded_field0,
                                               float embedded_field1);

struct test* (*structTest2FunctionPointer(void))(struct test tst,
                                                 char field0,
                                                 short field1,
                                                 int field2[3],
                                                 int *field3,
                                                 long long embedded_field0,
                                                 float embedded_field1);

union testunion (*unionTestFunctionPointer(void))(union testunion *tst,
                                                  int embedded_field0,
                                                  float embedded_field1);

union testunion* (*unionTest2FunctionPointer(void))(union testunion tst,
                                                    int embedded_field0);