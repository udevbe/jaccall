#include <stdlib.h>
#include "testing.h"

struct test doStaticTest(struct test *tst,
                         char field0,
                         short field1,
                         int field2[3],
                         int *field3) {

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

    return some_test;
}

struct test *doStaticTest2(struct test tst,
                           char field0,
                           short field1,
                           int field2[3],
                           int *field3) {

    struct test *some_test = malloc(sizeof(struct test));

    tst.field0 = field0;
    tst.field1 = field1;
    tst.field2[0] = field2[0];
    tst.field2[1] = field2[1];
    tst.field2[2] = field2[2];
    tst.field3 = field3;

    some_test->field0 = tst.field0;
    some_test->field1 = tst.field1;
    some_test->field2[0] = tst.field2[0];
    some_test->field2[1] = tst.field2[1];
    some_test->field2[2] = tst.field2[2];
    some_test->field3 = tst.field3;

    return some_test;
}