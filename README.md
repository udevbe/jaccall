Intro
=====

Jaccall makes C libraries accessible from Java without the need to write any native code. It is project similar to JNA or BridJ.

Status: 
- No release
- In development 
- Feature complete [ Linker API, Pointer API, Struct API, Function Pointer API ]
- [![Build Status](https://travis-ci.org/Zubnix/jaccall.svg?branch=master)](https://travis-ci.org/Zubnix/jaccall)

Jaccall's does not try to be Java, but instead tries to make C accessible in Java.
 - What you allocate, you must free yourself. watch out for memory leaks!
 - Cast to and from anything to anything. Watch out for cast mismatches!
 - Read and write to and from anything to anything. Watch out for segfaults!

Design goals:
 - Simple usage.
 - Simple runtime API.
 - No config files.
 - C only.
 - Support for Win/Lin/Mac/Android - x86, x86_64, armsf, armhf.
 - Support for all common use cases: unions, callbacks, pointer-to-pointer, ...
 
#### Comparison with other libraries

Jaccall was born out of a frustration with existing solutions. Existing solutions have the nasty trade-off of having a complete but cumbersome API and slow runtime, or have excellent speed and good API but suffer from scope creep while lacking armhf support.

Jaccall tries to remedy this by strictly adhering to the KISS princicple.

# Overview

- [Linker API](#linker-api)
  - [A linker example](#a-linker-example)
  - [Mapping](#mapping)
  - [By value By reference](#by-value-by-reference)
  - [Internals](#internals)
- [Pointer API](#pointer-api)
  - [A pointer example](#a-pointer-example)
  - [Stack vs Heap](#stack-vs-heap)
  - [Memory read write](#memory-read-write)
  - [Arrays](#arrays)
  - [Address manipulation](#address-manipulation)
  - [Pointer types](#pointer-types)
- [Struct API](#struct-api)
  - [A struct example](#a-struct-example)
  - [Usage](#usage)

# Linker API

The linker API forms the basis of all native method invocation. Without it, you wouldn't be able to call any native methods. 

To call a C method, we must create a Java class where we define what C method we are interested in, what they look like and where they can be found. This is done by mapping Java methods to C methods, and providing additional information through annotations.

#### A linker example

C

library: `libsomething.so`

header: `some_header.h`
```C
struct test {
    char field0;
    unsigned short field1;
    int field2[3];
    int *field3;
};
...
struct test do_something(struct test* tst,
                         char field0,
                         unsigned short field1,
                         int* field2,
                         int* field3);
```

Java
`SomeHeader.java`
```Java
@Lib("something")
public class SomeHeader {
    static {
        Linker.link(SomeHeader.class);
    }
    
    @ByVal(StructTest.class)
    public native long do_something(@Ptr(StructTest.class) long tst,
                                    byte field0,
                                    @Unsigned short field1,
                                    @Ptr(int.class) long field2,
                                    @Ptr(int.class) long field3);
}
```
This Java class exposes the C header file `some_header.h` to the Java side and informs the linker where these symbols (methods) can be resolved. This is done by providing the `@Lib(...)` annotation who's value must match the name part of `libsomething.so`. This whole flow is triggered by calling `Linker.link(...)`. 

Don't worry about the struct, that is handled in the [Struct API](#struct-api).

In order to pass data back and forth between Java and C, there are a few mapping rules to keep in mind.

- Java method name must match C method name
- Java method must be declared `native`
- Java method must be declared `public`
- Java method must only consist of a specific set of primitives for both arguments and return type.

#### Mapping

The Java mapping tries to match it's C counterpart as close as possible. There are however a few non intuitive exceptions. Let's have a look on how C types map to their Java counterpart.

| C | Java |
|---|------|
| unsigned char or char | byte |
| unsigned char | @Unsigned byte |
| short | short |
| unsigned short | @Unsigned short |
| int | int|
| unsigned int | @Unsigned int|
| long | long |
| unsigned long | @Unsigned long |
| long long | @Lng long |
| unsigned long long | @Unsigned @Lng long |
| float | float |
| double | double |
| struct foo | @ByVal(Foo.class) long |
| union bar | @ByVal(Bar.class) long |
| foo* | @Ptr(Foo.class) long|

The Java primitive types `boolean` and `char` do not have a corresponding C type and are not allowed.

The class argument for `@Ptr` is optional.

Arrays should be mapped as a pointer of the same type.

#### By value By reference

Java does not support the notion of passing by reference or by value. By default, all method arguments are passed by value in Java, inlcuding POJOs which are actually pointers internally. This limits the size of a single argument in Java to 64-bit. As such, Jaccall can not pass or return a C struct by value. Jaccall works around this problem by allocating heap memory and copyin/reading struct-by-value data. Jaccall must then only pass a pointer between Java and the native side. 

The drawback of this approach is that all returned struct-by-value data must be freed manually!

#### Internals

Jaccall has a compile time step to perform both fail-fast compile time checks and Java source code generation.
To aid the `Linker` in processing a natively mapped Java class, the `LinkerGenerator` does an initial pass over any `@Lib` annotated class to verify it's integrity and mapping rules. If this succeeds, it does a second pass and generates a `Foo_Jaccall_LinkSymbols.java` soure file for every `Foo.java` annotated with `@Lib`. This file should not be used by application code. This file contains linker data to aid the `Linker` in linking the required native Java methods to it's C counterpart.

For every mapped method, 4 parts of linker data are generated.
- The method name (the C symbol name).
- The number of arguments.
- A LibFFI call interface.
- A JNI signature.

If we reiterate our first mapping example

C `some_header.h`
```C
struct test {
    char field0;
    unsigned short field1;
    int field2[3];
    int *field3;
};
...
struct test do_something(struct test* tst,
                         char field0,
                         unsigned short field1,
                         int* field2,
                         int* field3);
```

Java `SomeHeader.java`
```Java
@ByVal(StructTest.class)
public native long do_something(@Ptr(StructTest.class) long tst,
                                byte field0,
                                short field1,
                                @Ptr(int.class) long field2,
                                @Ptr(int.class) long field3);
```

The generated linker data for this mapping:
`SomeHeader_Jaccall_LinkSymbols.java`
```Java
...
@Generated("com.github.zubnix.jaccall.compiletime.LinkerGenerator")
public final class SomeHeader_Jaccall_LinkSymbols extends LinkSymbols {
    public SomeHeader_Jaccall_LinkSymbols() {
        super(new String[]{"do_something"},
              new byte[]{5},
              new long[]{JNI.ffi_callInterface(StructTest.FFI_TYPE,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_SINT8,
                                               JNI.FFI_TYPE_UINT16,
                                               JNI.FFI_TYPE_POINTER,
                                               JNI.FFI_TYPE_POINTER)},
              new String[]{"(JBSJJ)J"});
    }
}
```

- `"do_something"` The name of the method
- `5` The number of arguments
- `JNI.ffi_callInterface(...)` The libffi call interface.
- `"(JBSJJ)J"` The JNI method signature.

Linker data of different methods matches on array index.

# Pointer API

#### A pointer example

C
```C
...
size_t int_size = sizeof(int);
void* void_p = malloc(int_size);
int* int_p = (int*) void_p;
...
free(int_p);
```
This example is pretty self explenatory. A new block of memory is allocated of size 'int'. This block of memory is then cast to a pointer of type int, and finally the memory is cleaned up.

Using Jaccall this translates to
```Java
//(Optional) Define a static import of the Pointer and Size classes 
//to avoid prefixing all static method calls.
import static com.github.zubnix.jaccall.Pointer.*
import static com.github.zubnix.jaccall.Size.*
...
//Calculate the size of Java type `Integer` wich corresponds to a C int.
int int_size = sizeof((Integer)null);
//Allocate a new block of memory, using `int_size` as it's size.
Pointer<Void> void_p = malloc(int_size);
//Do a pointer cast of the void pointer `void_p` to a pointer of type `Integer`.
Pointer<Integer> int_p = void_p.castp(Integer.class);
...
//free the memory pointed to by `int_p`
int_p.close();
```

#### Stack vs Heap

C has the concept of stack and heap allocated memory. Unfortunately this doesn't translate well in Java. Jaccall tries to alleviate this by defining a `Pointer<...>` as an `AutoClosable`. Using Java's try-with-resource concept, we can mimic the concept of stack allocated memory.

C
```C
int some_int = 5;
int* int_p = &some_int;
...
//`int_p` becomes invalid once method ends
```

Using Jaccall this translates to
```Java
//(Optional) Define a static import of the Pointer class to avoid prefixing all static method calls.
import static com.github.zubnix.jaccall.Pointer.*
...
//define an integer
int some_int = 5;
//allocate a new block of scoped memory with `some_int` as it's value.
try(Pointer<Integer> int_p = nref(some_int)){
...
}
//`int_p` becomes invalid once try block ends.
```

There are some notable differences between the C and Java example. In the C example, only one block of memory is used to define `some_int`, `int_p` is simply a reference to this memory. This block of memory is method scoped (stack allocated). Once the method exits, the memory is cleaned up. 

On the Java side however things are a bit different. A Java object (primitive) is defined as `some_int`. Next a new block of memory `int_p` is allocated on the heap, and the value of `some_int` is copied into it. This operation is reflected in the call `Pointer.nref(some_int)`. Because we defined `int_p` inside a try-with-resources, it will be freed automatically with a call to `close()` once the try block ends.

It is important to notice that there is nothing special about `Pointer.nref(some_int)`. It's merely a shortcut for
```Java
Pointer.malloc(Size.sizeof(some_int)).castp(Integer.class).write(some_int);
```

#### Memory read write

Let's extend our first basic example and add some read and write operations.

C
```C
...
size_t int_size = sizeof(int);
void* void_p = malloc(int_size);
int* int_p = (int*) void_p;
*int_p = 5;
int int_value = *int_p;
...
free(int_p);
```

The equivalent Java code:
```Java
import static com.github.zubnix.jaccall.Pointer.*
import static com.github.zubnix.jaccall.Size.*
...
int int_size = sizeof((Integer)null);
Pointer<Void> void_p = malloc(int_size);
Pointer<Integer> int_p = void_p.castp(Integer.class);
//write an int with value 5 to memory
int_p.write(5);
//read (dereference the pointer) an int from memory
int int_value = int_p.dref();
...
int_p.close();
```

The data that can be written and read from a pointer object in Jaccall depends on data type it refers to. This is why it's necessary that we perform a pointer cast using `castp(Integer.class)`. This creates a new pointer object that can read and write integers.

There are 3 different cast operations that can be performed on a pointer object.
 - an ordinary cast, using `cast(Class<?>)`. Cast a pointer to any primitive or struct type.
 - a pointer cast, using `castp(Class<?>)`. Cast a pointer to a pointer of another type.
 - a pointer to pointer cast, using `castpp()`. Cast a pointer to a pointer-to-pointer.

Starting from our basic example
```Java
import static com.github.zubnix.jaccall.Pointer.*
import static com.github.zubnix.jaccall.Size.*
...
int int_size = sizeof((Integer)null);
Pointer<Void> void_p = malloc(int_size);

//Perform a pointer cast, the resulting pointer can be used to read and write an integer.
Pointer<Integer> int_p = void_p.castp(Integer.class);
int_p.write(5);
int int_value = int_p.dref();

//Perform a pointer to pointer cast.
Pointer<Pointer<Integer>> int_pp = int_p.castpp();
//Dereferencing will cause a pointer object to be created with address 5, or possibly even segfault on a 64-bit system!
Pointer<Integer> bad_int_p = int_pp.dref();

//Perform an ordinary cast, `some_long` will now contain the address of our `int_p` pointer!
long some_long = int_p.cast(Long.class);
...
int_p.close();
```

In most cases, an ordinary cast using `cast(Class<?>)` will not be needed.

Beware that when casting to a pointer-to-pointer using `castp(Pointer.class)`, you will end up with a `Pointer<Pointer<?>>` object. You will be able to dereference the `Pointer<Pointer<?>>` object to the underlying `Pointer<?>`, but you will not be able to write or dereference this resulting pointer as Jaccall does not know what type it should refer too. Internally Jaccall will represent the `Pointer<?>` object as a `Pointer<Void>`.

It might not be immediatly obvious at first but using `castp(Class<?>)` and `castpp()` we can cast any pointer to any other type of pointer-to-pointer-to-pointer ad infinitum.

An example where we receive an address from a jni library. We know the address represents a `char***`, and as such want to create a `Pointer<Pointer<Pointer<Byte>>>`.
```Java
//get a native address from a jni library.
long some_native_address = ...;
//wrap the address in a untyped pointer
Pointer<Void> void_p = Pointer.wrap(some_native_address);
//cast to a byte pointer
Pointer<Byte> byte_p = void_p.castp(Byte.class);
//cast to a pointer-to-pointer
Pointer<Pointer<Byte>> byte_pp = byte_p.castpp();
//cast to a pointer-to-pointer-to-pointer
Pointer<Pointer<Pointer<Byte>>> byte_ppp = byte_pp.castpp();
```
We can rewrite the above example more briefly
```Java
import static com.github.zubnix.jaccall.Pointer.*
...
long some_native_address = ...;
//wrap in a pointer-to-pointer-to-char pointer
Pointer<Pointer<Pointer<Byte>>> byte_ppp = wrap(Byte.class,some_native_address).castpp().castpp();
```

#### Arrays

Up until now we've worked with single element pointers. Because a C array can be represented as a pointer, accessing a C array is surprisingly easy. Our pointer object knows the size of the type it's refering to, which in turn makes indexed read and writes straightforward.

We reiterate our previous read/write example, only this time we allocate space for multiple integers.
```Java
import static com.github.zubnix.jaccall.Pointer.*;
import static com.github.zubnix.jaccall.Size.*;
...
int int_size = sizeof((Integer)null);
//allocate space for 3 integers
Pointer<Void> void_p = malloc(int_size*3);
//cast to an ordinary int pointer
Pointer<Integer> int_p = void_p.castp(Integer.class);

//write an int with value 5 to index 0.
int_p.writei(0,5);
//write an int with value 6 to index 1.
int_p.writei(1,6);
//write an int with value 7 to index 2.
int_p.writei(2,6);

//dereference the pointer at index 0
int int_value_0 = int_p.dref(0);
//dereference the pointer at index 1
int int_value_1 = int_p.dref(1);
//dereference the pointer at index 2
int int_value_0 = int_p.dref(2);
...
int_p.close();
```

We can also use `Pointer.nref(...)` to allocate an array.
```Java
import static com.github.zubnix.jaccall.Pointer.*;
...
//nref uses a vararg parameter, so we can use it with both arrays and classic function arguments
Pointer<Integer> int_p_varargs = nref(1,2,3,4,5);
int[] array = {1,2,3,4,5);
Pointer<Integer> int_p_array = nref(array);
```

#### Address manipulation

In C, one can read and change the actual address value of a pointer. In Jaccall this is no different. The pointer object exposes it's address either directly through an `address` field of type `long`, or it can be casted to a long.
```Java
Pointer<Void> void_pointer = ...
//`void_pointer_adr` now contains the actual address of `void_pointer`
long void_pointer_adr = void_pointer.address;
//`void_pointer_adr_cast` now contains exactly the same value as `void_pointer_adr`.
long void_pointer_adr_cast = void_pointer.cast(Long.class)'
```

We can also easily offset a pointer address while keeping the type it points to.
```Java
Pointer<String> char_pointer = ...
//`char_pointer_offset` address is now incremented by 2 compared to `char_pointer` address.
Pointer<String> char_pointer_offset = char_pointer.offset(2);
```

#### Pointer types

A pointer object only supports a limited amount of Java types it can refer to. This is because it has to perform a mapping operation from the underlying C type to the equivalent Java type.

Following types are supported

| C | Java|
-----|-----
| void | Void or void |
| char | Byte or byte | 
| unsigned short or short | Short or short | 
| unsgined int or int | Integer or int |
| unsigned long or long | CLong | 
| unsigned long long or long long | Long or long | 
| float | Float or float | 
| double | Double or double | 
| foo* | Pointer | 
| char* | String |

Java primitives like boolean (Boolean) or char (Character) are not supported for the simple reason that they do not have a good C counterpart. A boolean type does not exist in C, and a Java char is actually an unsigned 16-bit integer that is used as an utf-16 character as opposed to C's 8-bit char type.

# Struct API

#### A struct example

Jaccall allows you to map any struct or union type in Java. Let's have a look at our previous example that contained a struct definition:

C
```C
struct test {
    char field0;
    unsigned short field1;
    int field2[3];
    int *field3;
};
...
```
Mapping this struct in Java using Jaccall
```Java
...
import static com.github.zubnix.jaccall.CType.CHAR;
import static com.github.zubnix.jaccall.CType.INT;
import static com.github.zubnix.jaccall.CType.POINTER;
import static com.github.zubnix.jaccall.CType.UNSIGNED_SHORT;
...
@Struct(value = {
    @Field(type = CHAR,
           name = "field0"),
    @Field(type = UNSIGNED_SHORT,
           name = "field1") ,
    @Field(type = INT,
           cardinality = 3,
           name = "field2"),
    @Field(type = POINTER,
           dataType = int.class,
           name = "field3")
})
public class Test extends Test_Jaccall_StructType {
}
```

Mapping a union is completely analogue.
C `some_header.h`
```C
union test {
    char field0;
    int field1;
};
...
```

In Java this becomes
```Java
...
import static com.github.zubnix.jaccall.CType.CHAR;
import static com.github.zubnix.jaccall.CType.INT;
...
@Struct(value = {
    union = true,
    @Field(type = CHAR,
           name = "field0"),
    @Field(type = INT,
           name = "field1"),
})
public class Test extends Test_Jaccall_StructType {
}
```

The `@Struct` annotation defines the layout of the native C struct in Java. This layout is parsed during compilation to generate accessor code. This generated code is put in a Java class with name `Foo_Jaccall_StructType`, where `Foo` is the name of the class that has the `@Struct` annotation. To use this accessor code, simply extend the generated class. In our exmaple this becomes `extends Test_Jaccall_StructType`.

The generated accessor class is part of the internal Jaccall API and should never be used directly. Instead always inherit from it.

The following rules apply when annotating a class with `@Struct`.
- A class annotated with `@Struct` must have a default no-arg constructor.
- A class annotated with `@Struct` must not be abstract.
- A class annotated with `@Struct` must have at least one `@Field`.
- A class annotated with `@Struct` must extend the equivalent generated accessor class.
- A class annotated with `@Struct` must have unique `@Field` names.
- A class annotated with `@Struct` must not have a static field with name `SIZE`.
- A class annotated with `@Struct` must be public.
- A class annotated with `@Struct` must be a class.
- A class annotated with `@Struct` must be a top level class.

#### Usage

Using a Jaccall struct in Java is very similar as how you would use a C struct. You can create a new one directly

C
```C
struct test testStruct;
testStruct.field0 = (char)123;
...
int field1 = testStruct.field1;
```

Java
```Java
Test testStruct = new Test();
testStruct.field0((byte)123);
...
int field1 = testStruct.field1();
```

or you can allocate a block of memory first, and map it as a struct.

C
```C
void* voidPointer = malloc(sizeof(struct test));
struct test *testPointer = (struct test *) voidPointer;
struct test testStruct = *testPointer;
testStruct.field0 = (char)123;
...
int field1 = testStruct.field1;
```

Java
```Java
Pointer<Void> voidPointer = Pointer.malloc(Test.SIZE);
Pointer<Test> testPointer = voidPointer.pcast(Test.class);
Test testStruct = testPointer.dref();
testStruct.field0((byte)123);
...
int field1 = testStruct.field1();
```

MORE TODO
