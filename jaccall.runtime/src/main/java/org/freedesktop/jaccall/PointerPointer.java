package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


final class PointerPointer<T> extends Pointer<Pointer<T>> {
    PointerPointer(@Nonnull final Type type,
                   final long address,
                   final boolean autoFree) {
        super(type,
              address,
              autoFree,
              Size.sizeof((Pointer) null));
    }

    @Nonnull
    @Override
    public Pointer<T> get() {
        return get(0);
    }

    @Nonnull
    @Override
    public Pointer<T> get(@Nonnegative final int index) {

        final long val = JNI.getPointer(this.address,
                                        index);

        //handle untyped pointers
        final Type genericClass;
        if (this.type instanceof ParameterizedType) {
            genericClass = this.type;
        }
        else {
            final Class<?> classType = toClass(this.type);
            genericClass = classType.getGenericSuperclass();
        }

        final Type type;
        if (genericClass != null && genericClass instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) genericClass;
            type = parameterizedType.getActualTypeArguments()[0];
        }
        else {
            //untyped pointer
            type = Void.class;
        }

        return wrap(type,
                    val,
                    false);
    }

    @Override
    public void set(@Nonnull final Pointer<T> val) {
        set(0,
            val);
    }

    @Override
    public void set(@Nonnegative final int index,
                    @Nonnull final Pointer<T> val) {
        JNI.setPointer(this.address,
                       index,
                       val.address);
    }
}
