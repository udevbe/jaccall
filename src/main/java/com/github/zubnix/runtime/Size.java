package com.github.zubnix.runtime;

import com.github.zubnix.runtime.api.CLong;
import com.github.zubnix.runtime.api.Pointer;
import com.github.zubnix.runtime.api.Struct;
import com.github.zubnix.runtime.api.Union;

public class Size {

    public static long sizeOf(final Object object) {

        final long size;

        final Class<?> type = object.getClass();
        if (Number.class.isAssignableFrom(type)) {
            if (Byte.class.equals(type)) {
                size = 1;
            }
            else if (Short.class.equals(type) || Character.class.equals(type)) {
                size = 2;
            }
            else if (Integer.class.equals(type) || Float.class.equals(type)) {
                size = 4;
            }
            else if (Long.class.equals(type) || Double.class.equals(type)) {
                size = 8;
            }
            else if (CLong.class.equals(type)) {
                size = sizeOf((CLong) object);
            }
            else {
                throw new IllegalArgumentException("Unsupported Number type " + type);
            }
        }
        else if (String.class.isAssignableFrom(type)) {
            size = sizeOf((String) object);
        }
        else {
            final Struct struct = type.getAnnotation(Struct.class);
            if (struct != null) {
                size = sizeOf(struct);
            }
            else {
                final Union union = type.getAnnotation(Union.class);
                if (union != null) {
                    size = sizeOf(union);
                }
                else {
                    throw new IllegalArgumentException("Type not annotated with Union or Struct " + type);
                }
            }
        }

        return size;
    }

    public static long sizeOf(final Byte b) {
        return 1;
    }

    public static long sizeOf(final Character b) {
        return 2;
    }

    public static long sizeOf(final Short b) {
        return 2;
    }

    public static long sizeOf(final Integer b) {
        return 4;
    }

    public static long sizeOf(final Float b) {
        return 4;
    }

    public static long sizeOf(final Long b) {
        return 8;
    }

    public static long sizeOf(final Double b) {
        return 8;
    }

    public static long sizeOf(final String string) {

    }

    public static long sizeOf(final Pointer pointer) {
        return JNI.sizeOfPointer();
    }

    public static long sizeOf(final CLong cLong) {
        return JNI.sizeOfCLong();
    }

    public static long sizeOf(final Struct struct) {
        //TODO use dyncall to calculate struct size
        return JNI.sizeOfStruct();
    }

    public static long sizeOf(final Union union) {
        //TODO use dyncall to calculate union size
        return JNI.sizeOfStruct();
    }
}
