#include "testing.h"

struct test doTest(struct test* tst,
                   char field0,
                   short field1,
                   int field2,
                   int* field3){
    struct test some_test = {
        .field0 = tst->field0,
        .field1 = tst->field1,
        .field2 = tst->field2,
        .field3 = tst->field3
    };

    tst->field0 = field0;
    tst->field1 = field1;
    tst->field2 = field2;
    tst->field3 = field3;

    return some_test;
}

struct test doStaticTest(struct test* tst,
                   char field0,
                   short field1,
                   int field2,
                   int* field3){
    return doTest(tst,field0,field1,field2,field3);
}