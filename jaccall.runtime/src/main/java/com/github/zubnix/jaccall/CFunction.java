package com.github.zubnix.jaccall;


import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

public class CFunction extends Pointer<CFunction> {

    //create cfunction from existing native function pointer
    CFunction(final long address,
              @Nonnull final ByteBuffer byteBuffer) {
        super(CFunction.class,
              address,
              byteBuffer);
    }

    @Override
    CFunction dref(@Nonnull final ByteBuffer byteBuffer) {
        return this;
    }

    @Override
    CFunction dref(@Nonnegative final int index,
                   @Nonnull final ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("subscripted value is pointer to function");
    }

    @Override
    void write(@Nonnull final ByteBuffer byteBuffer,
               @Nonnull final CFunction... val) {
        throw new UnsupportedOperationException("lvalue required as left operand of assignment");
    }

    @Override
    void writei(@Nonnull final ByteBuffer byteBuffer,
                @Nonnegative final int index,
                final CFunction... val) {

        final CFunction cFunction = new CFunction(123,
                                                  null);


        cFunction._(1,
                    (byte) 2);
        throw new UnsupportedOperationException("lvalue required as left operand of assignment");
    }

    //TODO use annotation processing to generate a statically typed caller method.
    public <T> T _(final Object... args) {
        //TODO if not overridden, call address as native function pointer

        return null;
    }
}
