struct test {
    char field0;
    short field1;
    int field2[3];
    int* field3;
};

union testunion {
    int field0;
    float field1;
};

struct test doStaticTest(struct test* tst,
                         char field0,
                         short field1,
                         int field2[3],
                         int* field3);

struct test* doStaticTest2(struct test tst,
                           char field0,
                           short field1,
                           int field2[3],
                           int* field3);

union testunion doStaticUnionTest(union testunion* tst,
                                  int field0,
                                  float field1);

union testunion* doStaticUnionTest2(union testunion tst,
                                    int field0);