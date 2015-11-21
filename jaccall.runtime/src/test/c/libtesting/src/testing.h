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