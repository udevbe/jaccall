package org.freedesktop.jaccall;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.freedesktop.jaccall.Size.sizeof;


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
    public Pointer<T> dref() {
        return dref(0);
    }

    @Nonnull
    @Override
    public Pointer<T> dref(@Nonnegative final int index) {

        final long val = JNI.readPointer(this.address,
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
    public void write(@Nonnull final Pointer<T> val) {
        writei(0,
               val);
    }

    @Override
    public void writei(@Nonnegative final int index,
                       @Nonnull final Pointer<T> val) {
        JNI.writePointer(this.address,
                         index,
                         val.address);
    }
}
