#include <stdlib.h>
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