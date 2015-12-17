struct test {
    char field0;
    short field1;
    int field2[3];
    int* field3;
};

struct test doStaticTest(struct test* tst,
                         char field0,
                         short field1,
                         int field2[3],
                         int* field3);