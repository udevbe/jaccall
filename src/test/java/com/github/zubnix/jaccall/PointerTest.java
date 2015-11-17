package com.github.zubnix.jaccall;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.ByteBuffer;

import static com.github.zubnix.jaccall.JNITestUtil.byteArrayAsPointer;
import static com.github.zubnix.jaccall.JNITestUtil.pointerOfPointer;
import static com.github.zubnix.jaccall.Pointer.calloc;
import static com.github.zubnix.jaccall.Pointer.malloc;
import static com.github.zubnix.jaccall.Pointer.ref;
import static com.github.zubnix.jaccall.Pointer.wrap;
import static com.github.zubnix.jaccall.Size.sizeof;
import static com.google.common.truth.Truth.assertThat;
import static java.nio.ByteBuffer.allocateDirect;
import static java.nio.ByteOrder.nativeOrder;

@RunWith(JUnit4.class)
public class PointerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testWrapByteBuffer() throws Exception {
        //given
        ByteBuffer byteBuffer = allocateDirect(5);
        byteBuffer.put(new byte[]{
                1, 1, 2, 3, 5
        });

        //when
        final Pointer<Void> voidPointer = wrap(byteBuffer);

        //then
        final long address = voidPointer.castT(Long.class);
        assertThat(address).isNotEqualTo(0L);
    }

    @Test
    public void testWrapTypedByteBuffer() throws Exception {
        //given
        ByteBuffer byteBuffer = allocateDirect(4 * 3).order(nativeOrder());

        int int0 = 4;
        int int1 = 5;
        int int2 = -10;

        byteBuffer.asIntBuffer()
                  .put(new int[]{int0, int1, int2});

        //when
        final Pointer<Integer> intPointer = wrap(Integer.class,
                                                 byteBuffer);

        //then
        final Integer integer0 = intPointer.dref();
        assertThat(integer0).isEqualTo(int0);

        final Integer integer1 = intPointer.dref(1);
        assertThat(integer1).isEqualTo(int1);

        final Integer integer2 = intPointer.dref(2);
        assertThat(integer2).isEqualTo(int2);
    }

    @Test
    public void testWrapAddress() throws Exception {
        //given
        final long pointer = byteArrayAsPointer(123,
                                                -94,
                                                43,
                                                58,
                                                0xFF);

        //when
        try (final Pointer<Void> voidPointer = wrap(pointer)) {

            //then
            assertThat(voidPointer.castT(Long.class)).isEqualTo(pointer);
        }
    }

    @Test
    public void testWrapTypedAddress() throws Exception {
        //given
        byte b0 = 123;
        byte b1 = -94;
        byte b2 = 43;
        byte b3 = 58;
        byte b4 = (byte) 0xFF;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);

        //when
        try (final Pointer<Byte> bytePointer = wrap(Byte.class,
                                                    pointer)) {

            //then
            assertThat(bytePointer.dref()).isEqualTo(b0);
            assertThat(bytePointer.dref(1)).isEqualTo(b1);
            assertThat(bytePointer.dref(2)).isEqualTo(b2);
            assertThat(bytePointer.dref(3)).isEqualTo(b3);
            assertThat(bytePointer.dref(4)).isEqualTo(b4);
        }
    }

    @Test
    public void testMalloc() throws Exception {
        //given
        final int   cLongSize = sizeof((CLong) null);
        final CLong cLong     = new CLong(123456);

        //when
        try (final Pointer<CLong> cLongPointer = malloc(cLongSize).castPT(CLong.class);) {
            cLongPointer.write(cLong);

            //then
            final long nativeCLongRead = JNITestUtil.readCLong(cLongPointer.address);
            assertThat(nativeCLongRead).isEqualTo(cLong.longValue());
        }
    }

    @Test
    public void testCalloc() throws Exception {
        //given
        final int longSize = sizeof(0L);
        final int nroLongs = 67;

        //when
        try (Pointer<Long> longPointer = calloc(nroLongs,
                                                longSize)
                .castPT(long.class);) {
            //then
            for (int i = 0; i < nroLongs; i++) {
                assertThat(longPointer.dref(i)).isEqualTo(0);
            }
        }
    }

    @Test
    public void testRefStructType() throws Exception {
        //TODO
//        throw new UnsupportedOperationException();
    }

    @Test
    public void testRefPointer() throws Exception {
        //given
        byte b0 = (byte) 0x8F;
        byte b1 = 0x7F;
        byte b2 = (byte) 0xF7;
        byte b3 = 0x00;
        byte b4 = 0x01;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);
        final Pointer<Byte> bytePointer = wrap(Byte.class,
                                               pointer);

        //when
        try (Pointer<Pointer<Byte>> bytePointerPointer = ref(bytePointer);) {
            //then
            assertThat(bytePointerPointer.dref().address).isEqualTo(pointer);
            assertThat(bytePointerPointer.dref()
                                         .dref(4)).isEqualTo(b4);
        }
    }

    @Test
    public void testRefByte() throws Exception {
        //given
        byte b0 = (byte) 0x12;
        byte b1 = 0x34;
        byte b2 = (byte) 0x56;
        byte b3 = 0x78;
        byte b4 = (byte) 0x90;

        //when
        try (Pointer<Byte> pointer = ref(b0,
                                         b1,
                                         b2,
                                         b3,
                                         b4);) {
            //then
            assertThat(pointer.address).isNotEqualTo(0L);

            assertThat(pointer.dref()).isEqualTo(b0);
            assertThat(pointer.dref(1)).isEqualTo(b1);
            assertThat(pointer.dref(2)).isEqualTo(b2);
            assertThat(pointer.dref(3)).isEqualTo(b3);
            assertThat(pointer.dref(4)).isEqualTo(b4);
        }
    }

    @Test
    public void testRefShort() throws Exception {
        //given
        short s0 = 0x1234;
        short s1 = 0x3456;
        short s2 = 0x5678;
        short s3 = 0x7890;
        short s4 = (short) 0x9012;

        //when
        try (Pointer<Short> pointer = ref(s0,
                                          s1,
                                          s2,
                                          s3,
                                          s4);) {
            //then
            assertThat(pointer.address).isNotEqualTo(0L);

            assertThat(pointer.dref()).isEqualTo(s0);
            assertThat(pointer.dref(1)).isEqualTo(s1);
            assertThat(pointer.dref(2)).isEqualTo(s2);
            assertThat(pointer.dref(3)).isEqualTo(s3);
            assertThat(pointer.dref(4)).isEqualTo(s4);
        }
    }

    @Test
    public void testRefChar() throws Exception {
        //given
        char c0 = 0x1234;
        char c1 = 0x3456;
        char c2 = 0x5678;
        char c3 = 0x7890;
        char c4 = 0x9012;

        //when
        try (Pointer<Character> pointer = ref(c0,
                                              c1,
                                              c2,
                                              c3,
                                              c4);) {
            //then
            assertThat(pointer.address).isNotEqualTo(0L);

            assertThat(pointer.dref()).isEqualTo(c0);
            assertThat(pointer.dref(1)).isEqualTo(c1);
            assertThat(pointer.dref(2)).isEqualTo(c2);
            assertThat(pointer.dref(3)).isEqualTo(c3);
            assertThat(pointer.dref(4)).isEqualTo(c4);
        }
    }

    @Test
    public void testRefInt() throws Exception {
        //given
        int i0 = 0x12345678;
        int i1 = 0x34567890;
        int i2 = 0x56789012;
        int i3 = 0x78901234;
        int i4 = 0x90123456;

        //when
        try (Pointer<Integer> pointer = ref(i0,
                                            i1,
                                            i2,
                                            i3,
                                            i4);) {
            //then
            assertThat(pointer.address).isNotEqualTo(0L);

            assertThat(pointer.dref()).isEqualTo(i0);
            assertThat(pointer.dref(1)).isEqualTo(i1);
            assertThat(pointer.dref(2)).isEqualTo(i2);
            assertThat(pointer.dref(3)).isEqualTo(i3);
            assertThat(pointer.dref(4)).isEqualTo(i4);
        }
    }

    @Test
    public void testRefFloat() throws Exception {
        //given
        float f0 = 0x12345678;
        float f1 = 0x34567890;
        float f2 = 0x56789012;
        float f3 = 0x78901234;
        float f4 = 0x90123456;

        //when
        try (Pointer<Float> pointer = ref(f0,
                                          f1,
                                          f2,
                                          f3,
                                          f4);) {
            //then
            assertThat(pointer.address).isNotEqualTo(0L);

            assertThat(pointer.dref()).isEqualTo(f0);
            assertThat(pointer.dref(1)).isEqualTo(f1);
            assertThat(pointer.dref(2)).isEqualTo(f2);
            assertThat(pointer.dref(3)).isEqualTo(f3);
            assertThat(pointer.dref(4)).isEqualTo(f4);
        }
    }

    @Test
    public void testRefLong() throws Exception {
        //given
        long l0 = 0x1234567890123456L;
        long l1 = 0x3456789012345678L;
        long l2 = 0x5678901234567890L;
        long l3 = 0x7890123456789012L;
        long l4 = 0x9012345678901234L;

        //when
        try (Pointer<Long> pointer = ref(l0,
                                         l1,
                                         l2,
                                         l3,
                                         l4);) {
            //then
            assertThat(pointer.address).isNotEqualTo(0L);

            assertThat(pointer.dref()).isEqualTo(l0);
            assertThat(pointer.dref(1)).isEqualTo(l1);
            assertThat(pointer.dref(2)).isEqualTo(l2);
            assertThat(pointer.dref(3)).isEqualTo(l3);
            assertThat(pointer.dref(4)).isEqualTo(l4);
        }
    }

    @Test
    public void testRefDouble() throws Exception {
        //given
        double d0 = 0x1234567890123456L;
        double d1 = 0x3456789012345678L;
        double d2 = 0x5678901234567890L;
        double d3 = 0x7890123456789012L;
        double d4 = 0x9012345678901234L;

        //when
        try (Pointer<Double> pointer = ref(d0,
                                           d1,
                                           d2,
                                           d3,
                                           d4);) {
            //then
            assertThat(pointer.address).isNotEqualTo(0L);

            assertThat(pointer.dref()).isEqualTo(d0);
            assertThat(pointer.dref(1)).isEqualTo(d1);
            assertThat(pointer.dref(2)).isEqualTo(d2);
            assertThat(pointer.dref(3)).isEqualTo(d3);
            assertThat(pointer.dref(4)).isEqualTo(d4);
        }
    }

    @Test
    public void testRefCLong() throws Exception {
        //given
        CLong cl0 = new CLong(0x12345678);
        CLong cl1 = new CLong(0x34567890);
        CLong cl2 = new CLong(0x56789012);
        CLong cl3 = new CLong(0x78901234);
        CLong cl4 = new CLong(0x90123456);

        //when
        try (Pointer<CLong> pointer = ref(cl0,
                                          cl1,
                                          cl2,
                                          cl3,
                                          cl4);) {
            //then
            assertThat(pointer.address).isNotEqualTo(0L);

            assertThat(pointer.dref()).isEqualTo(cl0);
            assertThat(pointer.dref(1)).isEqualTo(cl1);
            assertThat(pointer.dref(2)).isEqualTo(cl2);
            assertThat(pointer.dref(3)).isEqualTo(cl3);
            assertThat(pointer.dref(4)).isEqualTo(cl4);
        }
    }

    @Test
    public void testOffset() throws Exception {
        //given
        byte b0 = (byte) 0x12;
        byte b1 = 0x34;
        byte b2 = (byte) 0x56;
        byte b3 = 0x78;
        byte b4 = (byte) 0x90;

        //when
        try (Pointer<Byte> bytePointer = ref(b0,
                                             b1,
                                             b2,
                                             b3,
                                             b4);) {
            final Pointer<Byte> offsetBytePointer = bytePointer.offset(3);

            //then
            assertThat(offsetBytePointer.dref(0)).isEqualTo(b3);
        }
    }

    @Test
    public void testTCast() throws Exception {
        //given
        byte b0 = (byte) 0x8F;
        byte b1 = 0x7F;
        byte b2 = (byte) 0xF7;
        byte b3 = 0x00;
        byte b4 = 0x01;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);

        try (final Pointer<Void> voidPointer = wrap(pointer)) {
            //when
            final int integer = voidPointer.castT(int.class);

            //then
            assertThat(integer).isEqualTo((int) pointer);
        }
    }

    @Test
    public void testPtCast() throws Exception {
        //given
        byte b0 = (byte) 0x8F;
        byte b1 = 0x7F;
        byte b2 = (byte) 0xF7;
        byte b3 = 0x00;
        byte b4 = 0x01;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);

        try (final Pointer<Void> voidPointer = wrap(pointer)) {

            //when
            final Pointer<Integer> integerPointer = voidPointer.castPT(int.class);

            //then
            assertThat(integerPointer.dref()).isEqualTo(0x00F77F8F);//b3+b2+b1+b0 (little endian)
        }
    }

    @Test
    public void testPpCast() throws Exception {
        //given
        byte b0 = (byte) 0x8F;
        byte b1 = 0x7F;
        byte b2 = (byte) 0xF7;
        byte b3 = 0x00;
        byte b4 = 0x01;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);
        final long pointerOfPointer        = pointerOfPointer(pointer);
        final long pointerOfPointerPointer = pointerOfPointer(pointerOfPointer);


        //when
        try (final Pointer<Pointer<Pointer<Byte>>> bytePointerPointer = wrap(Byte.class,
                                                                             pointerOfPointerPointer).castPP()
                                                                                                     .castPP();
             final Pointer<Pointer> pointerPointer = wrap(Pointer.class,
                                                          pointerOfPointer)) {

            //then
            assertThat(bytePointerPointer.dref()
                                         .dref()
                                         .dref(4)).isEqualTo(b4);

            //throws error complaining about incomplete type
            expectedException.expect(IllegalStateException.class);
            expectedException.expectMessage("Can not dereference void pointer.");
            pointerPointer.dref()
                          .dref();
        }
    }

    @Test
    public void testWriteByte() throws Exception {
        //given
        byte b0 = 0x45;
        byte b1 = 0x67;
        byte b2 = 0x76;
        try (Pointer<Byte> bytePointer = malloc(sizeof(b0) * 3).castPT(byte.class)) {
            //when
            bytePointer.write(b0,
                              b1,
                              b2);
            //then
            assertThat(JNITestUtil.readByte(bytePointer.address)).isEqualTo(b0);
            assertThat(JNITestUtil.readByte(bytePointer.address + 1)).isEqualTo(b1);
            assertThat(JNITestUtil.readByte(bytePointer.address + 2)).isEqualTo(b2);
        }
    }

    @Test
    public void testWriteByteAtIndex() throws Exception {
        //given
        final int index  = 3;
        final int offset = index;
        byte      b0     = 0x45;
        byte      b1     = 0x67;
        byte      b2     = 0x76;
        try (Pointer<Byte> bytePointer = malloc((sizeof(b0) * 3) + offset).castPT(byte.class)) {
            //when
            bytePointer.writei(index,
                               b0,
                               b1,
                               b2);
            //then
            assertThat(JNITestUtil.readByte(bytePointer.address + offset)).isEqualTo(b0);
            assertThat(JNITestUtil.readByte(bytePointer.address + offset + 1)).isEqualTo(b1);
            assertThat(JNITestUtil.readByte(bytePointer.address + offset + 2)).isEqualTo(b2);
        }
    }

    @Test
    public void testWriteShort() throws Exception {
        //given
        short s0 = 0x4567;
        short s1 = (short) 0x8901;

        try (Pointer<Short> shortPointer = malloc(sizeof(s0) * 2).castPT(short.class)) {
            //when
            shortPointer.write(s0,
                               s1);

            //then
            assertThat(JNITestUtil.readByte(shortPointer.address)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(shortPointer.address + 1)).isEqualTo((byte) 0x45);
            assertThat(JNITestUtil.readByte(shortPointer.address + 2)).isEqualTo((byte) 0x01);
            assertThat(JNITestUtil.readByte(shortPointer.address + 3)).isEqualTo((byte) 0x89);
        }
    }

    @Test
    public void testWriteShortAtIndex() throws Exception {
        //given
        final int index  = 3;
        final int offset = index * 2;

        short s0 = 0x4567;
        short s1 = (short) 0x8901;

        try (Pointer<Short> shortPointer = malloc((sizeof(s0) * 2) + offset).castPT(short.class)) {
            //when
            shortPointer.writei(index,
                                s0,
                                s1);

            //then
            assertThat(JNITestUtil.readByte(shortPointer.address + offset)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(shortPointer.address + offset + 1)).isEqualTo((byte) 0x45);
            assertThat(JNITestUtil.readByte(shortPointer.address + offset + 2)).isEqualTo((byte) 0x01);
            assertThat(JNITestUtil.readByte(shortPointer.address + offset + 3)).isEqualTo((byte) 0x89);
        }
    }

    @Test
    public void testWriteChar() throws Exception {
        //given
        char c0 = 0x4567;
        char c1 = 0x8901;

        try (Pointer<Character> shortPointer = malloc((sizeof(c0) * 2)).castPT(char.class)) {
            //when
            shortPointer.write(c0,
                               c1);

            //then
            assertThat(JNITestUtil.readByte(shortPointer.address)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(shortPointer.address + 1)).isEqualTo((byte) 0x45);
            assertThat(JNITestUtil.readByte(shortPointer.address + 2)).isEqualTo((byte) 0x01);
            assertThat(JNITestUtil.readByte(shortPointer.address + 3)).isEqualTo((byte) 0x89);
        }
    }

    @Test
    public void testWriteCharAtIndex() throws Exception {
        //given
        final int index  = 3;
        final int offset = index * 2;

        char c0 = 0x4567;
        char c1 = 0x8901;

        try (Pointer<Character> shortPointer = malloc((sizeof(c0) * 2) + offset).castPT(char.class)) {
            //when
            shortPointer.writei(index,
                                c0,
                                c1);

            //then
            assertThat(JNITestUtil.readByte(shortPointer.address + offset)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(shortPointer.address + offset + 1)).isEqualTo((byte) 0x45);
            assertThat(JNITestUtil.readByte(shortPointer.address + offset + 2)).isEqualTo((byte) 0x01);
            assertThat(JNITestUtil.readByte(shortPointer.address + offset + 3)).isEqualTo((byte) 0x89);
        }
    }

    @Test
    public void testWriteInt() throws Exception {
        //given
        int i0 = 0x45678901;
        int i1 = 0x12345678;

        try (Pointer<Integer> integerPointer = malloc(sizeof(i0) * 2).castPT(int.class)) {
            //when
            integerPointer.write(i0,
                                 i1);
            //then
            assertThat(JNITestUtil.readByte(integerPointer.address)).isEqualTo((byte) 0x01);
            assertThat(JNITestUtil.readByte(integerPointer.address + 1)).isEqualTo((byte) 0x89);
            assertThat(JNITestUtil.readByte(integerPointer.address + 2)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(integerPointer.address + 3)).isEqualTo((byte) 0x45);

            assertThat(JNITestUtil.readByte(integerPointer.address + 4)).isEqualTo((byte) 0x78);
            assertThat(JNITestUtil.readByte(integerPointer.address + 5)).isEqualTo((byte) 0x56);
            assertThat(JNITestUtil.readByte(integerPointer.address + 6)).isEqualTo((byte) 0x34);
            assertThat(JNITestUtil.readByte(integerPointer.address + 7)).isEqualTo((byte) 0x12);
        }
    }

    @Test
    public void testWriteIntAtIndex() throws Exception {
        //given
        final int index  = 3;
        final int offset = index * 4;

        int i0 = 0x45678901;
        int i1 = 0x12345678;

        try (Pointer<Integer> integerPointer = malloc((sizeof(i0) * 2) + offset).castPT(int.class)) {
            //when
            integerPointer.writei(index,
                                  i0,
                                  i1);
            //then
            assertThat(JNITestUtil.readByte(integerPointer.address + offset)).isEqualTo((byte) 0x01);
            assertThat(JNITestUtil.readByte(integerPointer.address + offset + 1)).isEqualTo((byte) 0x89);
            assertThat(JNITestUtil.readByte(integerPointer.address + offset + 2)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(integerPointer.address + offset + 3)).isEqualTo((byte) 0x45);

            assertThat(JNITestUtil.readByte(integerPointer.address + offset + 4)).isEqualTo((byte) 0x78);
            assertThat(JNITestUtil.readByte(integerPointer.address + offset + 5)).isEqualTo((byte) 0x56);
            assertThat(JNITestUtil.readByte(integerPointer.address + offset + 6)).isEqualTo((byte) 0x34);
            assertThat(JNITestUtil.readByte(integerPointer.address + offset + 7)).isEqualTo((byte) 0x12);
        }
    }

    @Test
    public void testWriteFloat() throws Exception {
        //given
        float f0 = 0x45678901;
        float f1 = 0x12345678;

        try (Pointer<Float> floatPointer = malloc(sizeof(f0) * 2).castPT(float.class)) {
            //when
            floatPointer.write(f0,
                               f1);
            //then
            assertThat(JNITestUtil.readFloat(floatPointer.address)).isEqualTo(f0);
            assertThat(JNITestUtil.readFloat(floatPointer.address + 4)).isEqualTo(f1);
        }
    }

    @Test
    public void testWriteFloatAtIndex() throws Exception {
        //given
        final int index  = 3;
        final int offset = index * 4;

        float f0 = 0x45678901;
        float f1 = 0x12345678;

        try (Pointer<Float> floatPointer = malloc(sizeof(f0) * 2).castPT(float.class)) {
            //when
            floatPointer.writei(index,
                                f0,
                                f1);
            //then
            assertThat(JNITestUtil.readFloat(floatPointer.address + offset)).isEqualTo(f0);
            assertThat(JNITestUtil.readFloat(floatPointer.address + offset + 4)).isEqualTo(f1);
        }
    }

    @Test
    public void testWriteLong() throws Exception {

    }

    @Test
    public void testWriteLongAtIndex() throws Exception {

    }

    @Test
    public void testWriteDouble() throws Exception {

    }

    @Test
    public void testWriteDoubleAtIndex() throws Exception {

    }

    @Test
    public void testWritePointer() throws Exception {

    }

    @Test
    public void testWritePointerAtIndex() throws Exception {

    }

    @Test
    public void testWriteCLong() throws Exception {

    }

    @Test
    public void testWriteCLongAtIndex() throws Exception {

    }

    @Test
    public void testWriteStruct() throws Exception {

    }

    @Test
    public void testWriteStructAtIndex() throws Exception {

    }
}