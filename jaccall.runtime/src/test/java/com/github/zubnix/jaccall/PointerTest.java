package com.github.zubnix.jaccall;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import static com.github.zubnix.jaccall.JNITestUtil.byteArrayAsPointer;
import static com.github.zubnix.jaccall.JNITestUtil.pointerOfPointer;
import static com.github.zubnix.jaccall.Pointer.calloc;
import static com.github.zubnix.jaccall.Pointer.malloc;
import static com.github.zubnix.jaccall.Pointer.nref;
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
        final ByteBuffer byteBuffer = allocateDirect(5);
        byteBuffer.put(new byte[]{
                1, 1, 2, 3, 5
        });

        //when
        final Pointer<Void> voidPointer = wrap(byteBuffer);

        //then
        final long address = voidPointer.address;
        assertThat(address).isNotEqualTo(0L);
    }

    @Test
    public void testWrapTypedByteBuffer() throws Exception {
        //given
        final ByteBuffer byteBuffer = allocateDirect(4 * 3).order(nativeOrder());

        final int int0 = 4;
        final int int1 = 5;
        final int int2 = -10;

        byteBuffer.asIntBuffer()
                  .put(new int[]{int0, int1, int2});

        //when
        final Pointer<Integer> intPointer = wrap(Integer.class,
                                                 byteBuffer);

        //then
        //FIXME use jni to read values
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
            assertThat(voidPointer.address).isEqualTo(pointer);
        }
    }

    @Test
    public void testWrapTypedAddress() throws Exception {
        //given
        final byte b0 = 123;
        final byte b1 = -94;
        final byte b2 = 43;
        final byte b3 = 58;
        final byte b4 = (byte) 0xFF;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);

        //when
        try (final Pointer<Byte> bytePointer = wrap(Byte.class,
                                                    pointer)) {

            //then
            //FIXME use jni to read values
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
        try (final Pointer<CLong> cLongPointer = malloc(cLongSize).castp(CLong.class);) {
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
                                                longSize).castp(long.class);) {
            //then
            for (int i = 0; i < nroLongs; i++) {
                //FIXME use jni to read values
                assertThat(longPointer.dref(i)).isEqualTo(0);
            }
        }
    }

    @Test
    public void testRefStructType() throws Exception {
        //TODO add test

    }

    @Test
    public void testNrefPointer() throws Exception {
        //given
        final byte b0 = (byte) 0x8F;
        final byte b1 = 0x7F;
        final byte b2 = (byte) 0xF7;
        final byte b3 = 0x00;
        final byte b4 = 0x01;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);
        final Pointer<Byte> bytePointer = wrap(Byte.class,
                                               pointer);

        //when
        final Pointer<Pointer<Byte>> bytePointerPointer = nref(bytePointer);
        //then
        //FIXME use jni to read values
        //TODO add test dref Pointer
        assertThat(bytePointerPointer.dref().address).isEqualTo(pointer);
        assertThat(bytePointerPointer.dref()
                                     .dref(4)).isEqualTo(b4);
    }

    @Test
    public void testNrefByte() throws Exception {
        //given
        final byte b0 = (byte) 0x12;
        final byte b1 = 0x34;
        final byte b2 = (byte) 0x56;
        final byte b3 = 0x78;
        final byte b4 = (byte) 0x90;

        //when
        final Pointer<Byte> pointer = nref(b0,
                                           b1,
                                           b2,
                                           b3,
                                           b4);
        //then
        assertThat(pointer.address).isNotEqualTo(0L);
        //FIXME use jni to read values
        //TODO add test dref byte

        assertThat(pointer.dref()).isEqualTo(b0);
        assertThat(pointer.dref(1)).isEqualTo(b1);
        assertThat(pointer.dref(2)).isEqualTo(b2);
        assertThat(pointer.dref(3)).isEqualTo(b3);
        assertThat(pointer.dref(4)).isEqualTo(b4);
    }

    @Test
    public void testNrefShort() throws Exception {
        //given
        final short s0 = 0x1234;
        final short s1 = 0x3456;
        final short s2 = 0x5678;
        final short s3 = 0x7890;
        final short s4 = (short) 0x9012;

        //when
        final Pointer<Short> pointer = nref(s0,
                                            s1,
                                            s2,
                                            s3,
                                            s4);
        //then
        assertThat(pointer.address).isNotEqualTo(0L);
        //FIXME use jni to read values
        //TODO add test dref short

        assertThat(pointer.dref()).isEqualTo(s0);
        assertThat(pointer.dref(1)).isEqualTo(s1);
        assertThat(pointer.dref(2)).isEqualTo(s2);
        assertThat(pointer.dref(3)).isEqualTo(s3);
        assertThat(pointer.dref(4)).isEqualTo(s4);
    }

    @Test
    public void testNrefInt() throws Exception {
        //given
        final int i0 = 0x12345678;
        final int i1 = 0x34567890;
        final int i2 = 0x56789012;
        final int i3 = 0x78901234;
        final int i4 = 0x90123456;

        //when
        final Pointer<Integer> pointer = nref(i0,
                                              i1,
                                              i2,
                                              i3,
                                              i4);
        //then
        assertThat(pointer.address).isNotEqualTo(0L);
        //FIXME use jni to read values
        //TODO add test dref int

        assertThat(pointer.dref()).isEqualTo(i0);
        assertThat(pointer.dref(1)).isEqualTo(i1);
        assertThat(pointer.dref(2)).isEqualTo(i2);
        assertThat(pointer.dref(3)).isEqualTo(i3);
        assertThat(pointer.dref(4)).isEqualTo(i4);
    }

    @Test
    public void testNrefFloat() throws Exception {
        //given
        final float f0 = 0x12345678;
        final float f1 = 0x34567890;
        final float f2 = 0x56789012;
        final float f3 = 0x78901234;
        final float f4 = 0x90123456;

        //when
        final Pointer<Float> pointer = nref(f0,
                                            f1,
                                            f2,
                                            f3,
                                            f4);
        //then
        assertThat(pointer.address).isNotEqualTo(0L);
        //FIXME use jni to read values
        //TODO add test dref float

        assertThat(pointer.dref()).isEqualTo(f0);
        assertThat(pointer.dref(1)).isEqualTo(f1);
        assertThat(pointer.dref(2)).isEqualTo(f2);
        assertThat(pointer.dref(3)).isEqualTo(f3);
        assertThat(pointer.dref(4)).isEqualTo(f4);
    }

    @Test
    public void testNrefLong() throws Exception {
        //given
        final long l0 = 0x1234567890123456L;
        final long l1 = 0x3456789012345678L;
        final long l2 = 0x5678901234567890L;
        final long l3 = 0x7890123456789012L;
        final long l4 = 0x9012345678901234L;

        //when
        final Pointer<Long> pointer = nref(l0,
                                           l1,
                                           l2,
                                           l3,
                                           l4);
        //then
        assertThat(pointer.address).isNotEqualTo(0L);
        //FIXME use jni to read values
        //TODO add test dref long

        assertThat(pointer.dref()).isEqualTo(l0);
        assertThat(pointer.dref(1)).isEqualTo(l1);
        assertThat(pointer.dref(2)).isEqualTo(l2);
        assertThat(pointer.dref(3)).isEqualTo(l3);
        assertThat(pointer.dref(4)).isEqualTo(l4);

    }

    @Test
    public void testNrefDouble() throws Exception {
        //given
        final double d0 = 0x1234567890123456L;
        final double d1 = 0x3456789012345678L;
        final double d2 = 0x5678901234567890L;
        final double d3 = 0x7890123456789012L;
        final double d4 = 0x9012345678901234L;

        //when
        final Pointer<Double> pointer = nref(d0,
                                             d1,
                                             d2,
                                             d3,
                                             d4);
        //then
        assertThat(pointer.address).isNotEqualTo(0L);
        //FIXME use jni to read values
        //TODO add test dref double

        assertThat(pointer.dref()).isEqualTo(d0);
        assertThat(pointer.dref(1)).isEqualTo(d1);
        assertThat(pointer.dref(2)).isEqualTo(d2);
        assertThat(pointer.dref(3)).isEqualTo(d3);
        assertThat(pointer.dref(4)).isEqualTo(d4);

    }

    @Test
    public void testNrefCLong() throws Exception {
        //given
        final CLong cl0 = new CLong(0x12345678);
        final CLong cl1 = new CLong(0x34567890);
        final CLong cl2 = new CLong(0x56789012);
        final CLong cl3 = new CLong(0x78901234);
        final CLong cl4 = new CLong(0x90123456);

        //when
        final Pointer<CLong> pointer = nref(cl0,
                                            cl1,
                                            cl2,
                                            cl3,
                                            cl4);
        //then
        assertThat(pointer.address).isNotEqualTo(0L);
        //FIXME use jni to read values
        //TODO add test dref CLong

        assertThat(pointer.dref()).isEqualTo(cl0);
        assertThat(pointer.dref(1)).isEqualTo(cl1);
        assertThat(pointer.dref(2)).isEqualTo(cl2);
        assertThat(pointer.dref(3)).isEqualTo(cl3);
        assertThat(pointer.dref(4)).isEqualTo(cl4);

    }

    @Test
    public void testOffset() throws Exception {
        //given
        final byte b0 = (byte) 0x12;
        final byte b1 = 0x34;
        final byte b2 = (byte) 0x56;
        final byte b3 = 0x78;
        final byte b4 = (byte) 0x90;

        //when
        final Pointer<Byte> bytePointer = nref(b0,
                                               b1,
                                               b2,
                                               b3,
                                               b4);
        final Pointer<Byte> offsetBytePointer = bytePointer.offset(3);

        //then
        assertThat(offsetBytePointer.dref(0)).isEqualTo(b3);

    }

    @Test
    public void testCast() throws Exception {
        //given
        final byte b0 = (byte) 0x8F;
        final byte b1 = 0x7F;
        final byte b2 = (byte) 0xF7;
        final byte b3 = 0x00;
        final byte b4 = 0x01;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);

        try (final Pointer<Void> voidPointer = wrap(pointer)) {
            //when
            final int integer = (int) voidPointer.address;

            //then
            assertThat(integer).isEqualTo((int) pointer);
        }
    }

    @Test
    public void testCastp() throws Exception {
        //given
        final byte b0 = (byte) 0x8F;
        final byte b1 = 0x7F;
        final byte b2 = (byte) 0xF7;
        final byte b3 = 0x00;
        final byte b4 = 0x01;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);

        try (final Pointer<Void> voidPointer = wrap(pointer)) {

            //when
            final Pointer<Integer> integerPointer = voidPointer.castp(int.class);

            //then
            assertThat(integerPointer.dref()).isEqualTo(0x00F77F8F);//b3+b2+b1+b0 (little endian)
        }
    }

    @Test
    public void testCastpp() throws Exception {
        //given
        final byte b0 = (byte) 0x8F;
        final byte b1 = 0x7F;
        final byte b2 = (byte) 0xF7;
        final byte b3 = 0x00;
        final byte b4 = 0x01;

        final long pointer = byteArrayAsPointer(b0,
                                                b1,
                                                b2,
                                                b3,
                                                b4);
        final long pointerOfPointer        = pointerOfPointer(pointer);
        final long pointerOfPointerPointer = pointerOfPointer(pointerOfPointer);


        //when
        try (final Pointer<Pointer<Pointer<Byte>>> bytePointerPointer = wrap(Byte.class,
                                                                             pointerOfPointerPointer).castpp()
                                                                                                     .castpp();
             final Pointer<Pointer> pointerPointer = wrap(Pointer.class,
                                                          pointerOfPointer)) {

            //then
            assertThat(bytePointerPointer.dref()
                                         .dref()
                                         .dref(4)).isEqualTo(b4);

            //throws error complaining about incomplete type
            this.expectedException.expect(IllegalStateException.class);
            this.expectedException.expectMessage("Can not dereference void pointer.");
            pointerPointer.dref()
                          .dref();
        }
    }

    @Test
    public void testWriteByte() throws Exception {
        //given
        final byte b0 = 0x45;
        final byte b1 = 0x67;
        final byte b2 = 0x76;

        //when
        final Pointer<Byte> bytePointer = Pointer.nref(b0,
                                                       b1,
                                                       b2);

        //then
        assertThat(JNITestUtil.readByte(bytePointer.address)).isEqualTo(b0);
        assertThat(JNITestUtil.readByte(bytePointer.address + 1)).isEqualTo(b1);
        assertThat(JNITestUtil.readByte(bytePointer.address + 2)).isEqualTo(b2);
    }

    @Test
    public void testWriteByteAtIndex() throws Exception {
        //given
        final byte b0 = 0x45;
        final byte b1 = 0x67;
        final byte b2 = 0x76;

        try (Pointer<Byte> bytePointer = malloc(sizeof((Byte) null) * 6).castp(byte.class)) {
            //when
            bytePointer.writei(3,
                               b0);
            bytePointer.writei(4,
                               b1);
            bytePointer.writei(5,
                               b2);
            //then
            final int byteOffset = sizeof((Byte) null) * 3;

            assertThat(JNITestUtil.readByte(bytePointer.address + byteOffset)).isEqualTo(b0);
            assertThat(JNITestUtil.readByte(bytePointer.address + byteOffset + 1)).isEqualTo(b1);
            assertThat(JNITestUtil.readByte(bytePointer.address + byteOffset + 2)).isEqualTo(b2);
        }
    }

    @Test
    public void testWriteShort() throws Exception {
        //given
        final short s0 = 0x4567;
        final short s1 = (short) 0x8901;

        //when
        final Pointer<Short> shortPointer = Pointer.nref(s0,
                                                         s1);

        //then
        assertThat(JNITestUtil.readByte(shortPointer.address)).isEqualTo((byte) 0x67);
        assertThat(JNITestUtil.readByte(shortPointer.address + 1)).isEqualTo((byte) 0x45);
        assertThat(JNITestUtil.readByte(shortPointer.address + 2)).isEqualTo((byte) 0x01);
        assertThat(JNITestUtil.readByte(shortPointer.address + 3)).isEqualTo((byte) 0x89);
    }

    @Test
    public void testWriteShortAtIndex() throws Exception {
        //given
        final short s0 = 0x4567;
        final short s1 = (short) 0x8901;

        try (Pointer<Short> shortPointer = malloc(sizeof((Short) null) * 5).castp(short.class)) {
            //when
            shortPointer.writei(3,
                                s0);
            shortPointer.writei(4,
                                s1);

            //then
            final int byteOffset = sizeof((Short) null) * 3;

            assertThat(JNITestUtil.readByte(shortPointer.address + byteOffset)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(shortPointer.address + byteOffset + 1)).isEqualTo((byte) 0x45);
            assertThat(JNITestUtil.readByte(shortPointer.address + byteOffset + 2)).isEqualTo((byte) 0x01);
            assertThat(JNITestUtil.readByte(shortPointer.address + byteOffset + 3)).isEqualTo((byte) 0x89);
        }
    }

    @Test
    public void testWriteInt() throws Exception {
        //given
        final int i0 = 0x45678901;
        final int i1 = 0x12345678;

        //when
        final Pointer<Integer> integerPointer = Pointer.nref(i0,
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

    @Test
    public void testWriteIntAtIndex() throws Exception {
        //given
        final int i0 = 0x45678901;
        final int i1 = 0x12345678;

        try (Pointer<Integer> integerPointer = malloc(sizeof((Integer) null) * 5).castp(int.class)) {
            //when
            integerPointer.writei(3,
                                  i0);
            integerPointer.writei(4,
                                  i1);
            //then
            final int byteOffset = sizeof((Integer) null) * 3;

            assertThat(JNITestUtil.readByte(integerPointer.address + byteOffset)).isEqualTo((byte) 0x01);
            assertThat(JNITestUtil.readByte(integerPointer.address + byteOffset + 1)).isEqualTo((byte) 0x89);
            assertThat(JNITestUtil.readByte(integerPointer.address + byteOffset + 2)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(integerPointer.address + byteOffset + 3)).isEqualTo((byte) 0x45);

            assertThat(JNITestUtil.readByte(integerPointer.address + byteOffset + 4)).isEqualTo((byte) 0x78);
            assertThat(JNITestUtil.readByte(integerPointer.address + byteOffset + 5)).isEqualTo((byte) 0x56);
            assertThat(JNITestUtil.readByte(integerPointer.address + byteOffset + 6)).isEqualTo((byte) 0x34);
            assertThat(JNITestUtil.readByte(integerPointer.address + byteOffset + 7)).isEqualTo((byte) 0x12);
        }
    }

    @Test
    public void testWriteFloat() throws Exception {
        //given
        final float f0 = 0x45678901;
        final float f1 = 0x12345678;

        final Pointer<Float> floatPointer = Pointer.nref(f0,
                                                         f1);
        //then
        assertThat(JNITestUtil.readFloat(floatPointer.address)).isEqualTo(f0);
        assertThat(JNITestUtil.readFloat(floatPointer.address + 4)).isEqualTo(f1);
    }

    @Test
    public void testWriteFloatAtIndex() throws Exception {
        //given
        final float f0 = 0x45678901;
        final float f1 = 0x12345678;

        try (Pointer<Float> floatPointer = malloc(sizeof(sizeof((Float) null)) * 5).castp(float.class)) {
            //when
            floatPointer.writei(3,
                                f0);
            floatPointer.writei(4,
                                f1);
            //then
            final int offset = 3 * sizeof((Float) null);
            assertThat(JNITestUtil.readFloat(floatPointer.address + offset)).isEqualTo(f0);
            assertThat(JNITestUtil.readFloat(floatPointer.address + offset + 4)).isEqualTo(f1);
        }
    }

    @Test
    public void testWriteLong() throws Exception {
        //given
        final long l0 = 0x4567890123456789L;
        final long l1 = 0x1234567890123456L;

        //when
        final Pointer<Long> longPointer = Pointer.nref(l0,
                                                       l1);

        //then
        assertThat(JNITestUtil.readByte(longPointer.address)).isEqualTo((byte) 0x89);
        assertThat(JNITestUtil.readByte(longPointer.address + 1)).isEqualTo((byte) 0x67);
        assertThat(JNITestUtil.readByte(longPointer.address + 2)).isEqualTo((byte) 0x45);
        assertThat(JNITestUtil.readByte(longPointer.address + 3)).isEqualTo((byte) 0x23);
        assertThat(JNITestUtil.readByte(longPointer.address + 4)).isEqualTo((byte) 0x01);
        assertThat(JNITestUtil.readByte(longPointer.address + 5)).isEqualTo((byte) 0x89);
        assertThat(JNITestUtil.readByte(longPointer.address + 6)).isEqualTo((byte) 0x67);
        assertThat(JNITestUtil.readByte(longPointer.address + 7)).isEqualTo((byte) 0x45);

        assertThat(JNITestUtil.readByte(longPointer.address + 8)).isEqualTo((byte) 0x56);
        assertThat(JNITestUtil.readByte(longPointer.address + 9)).isEqualTo((byte) 0x34);
        assertThat(JNITestUtil.readByte(longPointer.address + 10)).isEqualTo((byte) 0x12);
        assertThat(JNITestUtil.readByte(longPointer.address + 11)).isEqualTo((byte) 0x90);
        assertThat(JNITestUtil.readByte(longPointer.address + 12)).isEqualTo((byte) 0x78);
        assertThat(JNITestUtil.readByte(longPointer.address + 13)).isEqualTo((byte) 0x56);
        assertThat(JNITestUtil.readByte(longPointer.address + 14)).isEqualTo((byte) 0x34);
        assertThat(JNITestUtil.readByte(longPointer.address + 15)).isEqualTo((byte) 0x12);
    }

    @Test
    public void testWriteLongAtIndex() throws Exception {
        //given

        final long l0 = 0x4567_8901_2345_6789L;
        final long l1 = 0x1234_5678_9012_3456L;

        try (Pointer<Long> longPointer = malloc((sizeof((Long) null) * 5)).castp(long.class)) {
            //when
            longPointer.writei(3,
                               l0);
            longPointer.writei(4,
                               l1);
            //then
            final int byteOffest = 3 * sizeof((Long) null);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest)).isEqualTo((byte) 0x89);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 1)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 2)).isEqualTo((byte) 0x45);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 3)).isEqualTo((byte) 0x23);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 4)).isEqualTo((byte) 0x01);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 5)).isEqualTo((byte) 0x89);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 6)).isEqualTo((byte) 0x67);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 7)).isEqualTo((byte) 0x45);

            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 8)).isEqualTo((byte) 0x56);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 9)).isEqualTo((byte) 0x34);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 10)).isEqualTo((byte) 0x12);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 11)).isEqualTo((byte) 0x90);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 12)).isEqualTo((byte) 0x78);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 13)).isEqualTo((byte) 0x56);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 14)).isEqualTo((byte) 0x34);
            assertThat(JNITestUtil.readByte(longPointer.address + byteOffest + 15)).isEqualTo((byte) 0x12);
        }
    }

    @Test
    public void testWriteDouble() throws Exception {
        //given
        final double d0 = 0x4567_8901_2345_6789L;
        final double d1 = 0x1234_5678_9012_3456L;

        //when
        final Pointer<Double> doublePointer = Pointer.nref(d0,
                                                           d1);

        //then
        assertThat((float) JNITestUtil.readDouble(doublePointer.address)).isEqualTo((float) d0);
        assertThat((float) JNITestUtil.readDouble(doublePointer.address + 8)).isEqualTo((float) d1);
    }

    @Test
    public void testWriteDoubleAtIndex() throws Exception {
        //given

        final double d0 = 0x4567_8901_2345_6789L;
        final double d1 = 0x1234_5678_9012_3456L;

        try (Pointer<Double> doublePointer = malloc(sizeof((Double) null) * 5).castp(double.class)) {
            //when
            doublePointer.writei(3,
                                 d0);
            doublePointer.writei(4,
                                 d1);
            //then
            final int byteOffset = 3 * sizeof((Double) null);
            assertThat((float) JNITestUtil.readDouble(doublePointer.address + byteOffset)).isEqualTo((float) d0);
            assertThat((float) JNITestUtil.readDouble(doublePointer.address + byteOffset + sizeof((Double) null))).isEqualTo((float) d1);
        }
    }

    @Test
    public void testWritePointer() throws Exception {
        //given
        final long p0 = JNITestUtil.byteArrayAsPointer(0,
                                                       0,
                                                       0,
                                                       0,
                                                       0);
        final long p1 = JNITestUtil.byteArrayAsPointer(0,
                                                       0,
                                                       0,
                                                       0,
                                                       0);

        final Pointer<Pointer<Void>> floatPointer = Pointer.nref(wrap(p0),
                                                                 wrap(p1));
        //when
        //then
        assertThat(JNITestUtil.readPointer(floatPointer.address)).isEqualTo(p0);
        assertThat(JNITestUtil.readPointer(floatPointer.address + sizeof((Pointer) null))).isEqualTo(p1);
    }

    @Test
    public void testWritePointerAtIndex() throws Exception {
        //given
        final long p0 = JNITestUtil.byteArrayAsPointer(0,
                                                       0,
                                                       0,
                                                       0,
                                                       0);
        final long p1 = JNITestUtil.byteArrayAsPointer(0,
                                                       0,
                                                       0,
                                                       0,
                                                       0);

        try (Pointer<Pointer> pointerPointer = malloc(sizeof((Pointer) null) * 5).castp(Pointer.class)) {
            //when
            pointerPointer.writei(3,
                                  wrap(p0));
            pointerPointer.writei(4,
                                  wrap(p1));
            //then
            assertThat(JNITestUtil.readPointer(pointerPointer.address + 3 * sizeof((Pointer) null))).isEqualTo(p0);
            assertThat(JNITestUtil.readPointer(pointerPointer.address + 4 * sizeof((Pointer) null))).isEqualTo(p1);
        }
    }

    @Test
    public void testWriteCLong() throws Exception {
        //given
        final CLong cl0 = new CLong(0x45678901);
        final CLong cl1 = new CLong(0x12345678);

        //when
        final Pointer<CLong> cLongPointer = Pointer.nref(cl0,
                                                         cl1);

        //then
        assertThat(JNITestUtil.readCLong(cLongPointer.address)).isEqualTo(cl0.longValue());
        assertThat(JNITestUtil.readCLong(cLongPointer.address + sizeof((CLong) null))).isEqualTo(cl1.longValue());
    }

    @Test
    public void testWriteCLongAtIndex() throws Exception {
        //given
        final CLong cl0 = new CLong(0x45678901);
        final CLong cl1 = new CLong(0x12345678);

        try (Pointer<CLong> cLongPointer = malloc(sizeof(cl0) * 5).castp(CLong.class)) {
            //when
            cLongPointer.writei(3,
                                cl0);
            cLongPointer.writei(4,
                                cl1);
            //then
            assertThat(JNITestUtil.readCLong(cLongPointer.address + 3 * sizeof((CLong) null))).isEqualTo(cl0.longValue());
            assertThat(JNITestUtil.readCLong(cLongPointer.address + 4 * sizeof((CLong) null))).isEqualTo(cl1.longValue());
        }
    }

    @Test
    public void testWriteCString() throws Exception {
        //given
        final char[] chars = new char[]{'f', 'o', 'o', ' ', 'b', 'a', 'r'};
        final String s     = new String(chars);

        try (Pointer<String> stringPointer = malloc(sizeof(s)).castp(String.class)) {
            //when
            stringPointer.write(s);
            //then
            assertThat(JNITestUtil.readByte(stringPointer.address)).isEqualTo((byte) chars[0]);
            assertThat(JNITestUtil.readByte(stringPointer.address + 1)).isEqualTo((byte) chars[1]);
            assertThat(JNITestUtil.readByte(stringPointer.address + 2)).isEqualTo((byte) chars[2]);
            assertThat(JNITestUtil.readByte(stringPointer.address + 3)).isEqualTo((byte) chars[3]);
            assertThat(JNITestUtil.readByte(stringPointer.address + 4)).isEqualTo((byte) chars[4]);
            assertThat(JNITestUtil.readByte(stringPointer.address + 5)).isEqualTo((byte) chars[5]);
            assertThat(JNITestUtil.readByte(stringPointer.address + 6)).isEqualTo((byte) chars[6]);
            assertThat(JNITestUtil.readByte(stringPointer.address + 7)).isEqualTo((byte) 0);
        }
    }

    @Test
    public void testWriteCStringAtIndex() throws Exception {
        //given
        final int    index  = 3;
        final int    offset = index;
        final char[] chars  = new char[]{'f', 'o', 'o', ' ', 'b', 'a', 'r'};
        final String s      = new String(chars);

        try (Pointer<String> stringPointer = malloc(sizeof(s) + offset).castp(String.class)) {
            //when
            stringPointer.writei(index,
                                 s);
            //then
            assertThat(JNITestUtil.readByte(stringPointer.address + offset)).isEqualTo((byte) 'f');
            assertThat(JNITestUtil.readByte(stringPointer.address + offset + 1)).isEqualTo((byte) 'o');
            assertThat(JNITestUtil.readByte(stringPointer.address + offset + 2)).isEqualTo((byte) 'o');
            assertThat(JNITestUtil.readByte(stringPointer.address + offset + 3)).isEqualTo((byte) ' ');
            assertThat(JNITestUtil.readByte(stringPointer.address + offset + 4)).isEqualTo((byte) 'b');
            assertThat(JNITestUtil.readByte(stringPointer.address + offset + 5)).isEqualTo((byte) 'a');
            assertThat(JNITestUtil.readByte(stringPointer.address + offset + 6)).isEqualTo((byte) 'r');
            assertThat(JNITestUtil.readByte(stringPointer.address + offset + 7)).isEqualTo((byte) 0);
        }
    }

    @Test
    public void testWriteStruct() throws Exception {
        //TODO
        //throw new UnsupportedOperationException();
    }

    @Test
    public void testWriteStructAtIndex() throws Exception {
        //TODO
        //throw new UnsupportedOperationException();
    }

    @Test
    public void testWriteJObject() throws Exception {
        //given
        final List<String> strings = new LinkedList<>();
        final String       elm0    = "foo";
        strings.add(elm0);

        final Pointer<JObject> jObjectPointer = Pointer.nref(new JObject(strings));
        //when
        final Pointer<JObject> wrap = Pointer.wrap(JObject.class,
                                                   jObjectPointer.address);
        final List<String> stringsFromPointer = (List<String>) wrap.dref()
                                                                   .pojo();
        //then
        assertThat(stringsFromPointer.get(0)).isSameAs(elm0);
        assertThat(strings).isSameAs(stringsFromPointer);
    }

    @Test
    public void testWriteJObjectAtIndex() throws Exception {
        //given
        final List<String> strings0 = new LinkedList<>();
        final String       elm0     = "foo";
        strings0.add(elm0);

        final List<String> strings1 = new LinkedList<>();
        final String       elm1     = "bar";
        strings1.add(elm1);

        final Pointer<JObject> jObjectPointer = malloc(sizeof((JObject) null) * 5,
                                                       JObject.class);
        //when
        jObjectPointer.writei(3,
                              new JObject(strings0));
        jObjectPointer.writei(4,
                              new JObject(strings1));

        final Pointer<JObject> wrap = Pointer.wrap(JObject.class,
                                                   jObjectPointer.address);

        final List<String> strings0FromPointer = (List<String>) wrap.dref(3)
                                                                    .pojo();
        final List<String> strings1FromPointer = (List<String>) wrap.dref(4)
                                                                    .pojo();

        //then
        assertThat(strings0FromPointer.get(0)).isSameAs(elm0);
        assertThat(strings0).isSameAs(strings0FromPointer);

        assertThat(strings1FromPointer.get(0)).isSameAs(elm1);
        assertThat(strings1).isSameAs(strings1FromPointer);
    }
}