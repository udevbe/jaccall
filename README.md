Intro
=====

Experimental project for what is supposed to be a library similar to BridJ or JNA.

Goals are:
 - Simple usage.
 - Simple runtime API.
 - No config files.
 - Only C.
 - Linux only initially.
 - Support for Win/Lin/Mac - x86, x86_64, armsf, armhf.
 - Support for all common use cases: unions, callbacks, pointer-to-pointer, ...
 - Compile time annotation processor gathers all statically inferable information and generates runtime API independent code.

Jaccall's does not try be Java, but instead tries to make C accessible in Java.
This means:
 - What you allocate, you must free yourself. watch out for memory leaks!
 - Cast to and from anything to anything. Watch out for cast mismatches!
 - Read and write to and from anything to anything. Watch out for segfaults!

# Linker API

The linker API forms the basis of all native method invocation. Without it, you wouldn't be able to call any native methods. 

To call a C method, we must create a Java class where we define what C method we are interested in, what they look like and where they can be found. This is done by mapping Java methods to C methods, and providing additional information through annotations.

An example

C
```C
struct test {
    char field0;
    short field1;
    int field2;
    int* field3;
};
struct test doTest(struct test* tst,
                   char field0,
                   short field1,
                   int field2,
                   int* field3);
```

Java
```Java
@Lib("testing")
public class Testing {
    @ByVal(TestStruct.class)
    public native long doTest(@Ptr(TestStruct.class) long tst,
                              byte field0,
                              short field1,
                              int field2,
                              @Ptr(int.class) long field3);
}
```
The class exposes the C header file to the Java side. However, simply exposing C functions is not enough. The linker needs to know how these symbols (methods) can be resolved. This is done by providing the `@Lib(...)` annotation. This annotation defines the library where the mapped symbols can be found.

#### Mapping

The Java mapping tries to match it's C counterpart as close as possible. There are however a few non intuitive exceptions. Let's have a look on how C types map to their Java counterpart.

MORE TODO


# Struct API
TODO

# Pointer API

#### A basic example

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

#### Stack vs Heap.


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

#### Memory read/write

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
| char | Byte or byte | 
| unsigned short or short | Short or short | 
| unsgined int or int | Integer or int |
| unsigned long or long | CLong | 
| unsigned long long or long long | Long or long | 
| float | Float or float | 
| double | Double or double | 
| type* | Pointer | 
| char* | String |

TODO MORE
