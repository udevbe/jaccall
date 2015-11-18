#include <stdio.h>
#include "testing.h"

struct test doTest(struct test* tst,
                   char field0,
                   short field1,
                   int field2[3],
                   int* field3){
    fprintf(stderr, "calling doTest(struct test*=%p, char field0=%c, short field1=%d, int field2[3]=%p, int* field3=%p)\n",
    tst,field0,field1,field2,field3);

    tst->field0 = field0;
    tst->field1 = field1;
    tst->field2[0] = field2[0];
    tst->field2[1] = field2[1];
    tst->field2[2] = field2[2];
    tst->field3 = field3;

    struct test some_test = {
        .field0 = tst->field0,
        .field1 = tst->field1,
        .field2[0] = tst->field2[0],
        .field2[1] = tst->field2[1],
        .field2[2] = tst->field2[2],
        .field3 = tst->field3
    };

    fprintf(stderr, "return doTest struct test{ .field0 = %c, .field1=%d, .field2=%p, .field3=%p}",
    some_test.field0, some_test.field1, some_test.field2, some_test.field3);

    return some_test;
}

struct test doStaticTest(struct test* tst,
                         char field0,
                         short field1,
                         int field2[3],
                         int* field3){

    fprintf(stderr, "calling doStaticTest(struct test*=%p, char field0=%c, short field1=%d, int field2[3]=%p, int* field3=%p)\n",
    tst,field0,field1,field2,field3);

    tst->field0 = field0;
    tst->field1 = field1;
    tst->field2[0] = field2[0];
    tst->field2[1] = field2[1];
    tst->field2[2] = field2[2];
    tst->field3 = field3;

    struct test some_test = {
        .field0 = tst->field0,
        .field1 = tst->field1,
        .field2[0] = tst->field2[0],
        .field2[1] = tst->field2[1],
        .field2[2] = tst->field2[2],
        .field3 = tst->field3
    };

    fprintf(stderr, "return doStaticTest struct test{ .field0 = %c, .field1=%d, .field2=%p, .field3=%p}",
    some_test.field0, some_test.field1, some_test.field2, some_test.field3);

    return some_test;
}