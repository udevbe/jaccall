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
 - Read to and from anything to anything. Watch out for segfaults!

# Pointer API
===========

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


C has the concept of stack and heap allocated memory. Unfortunately this doesn't translate well in Java. Jaccall tries to alleviate this by defining a `Pointer<...>` as an `AutoClosable`.
Using Java's try-with-resource concept, we can mimic the concept of stack allocated memory.

C
```C
int some_int = 5;
int* int_p = &some_int;
...
//`int_p` becomes invalid once method ends
```

Using Jaccall this translates to
```Java
//(Optional) Define a static import of the Pointer and Size classes 
//to avoid prefixing all static method calls.
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

There are some notable differences between the C and Java example. In the C example, only one block of memory is used to define `some_int`, `int_p` is simply a reference to this memory. This block of memory is method scoped. Once the method exits, the memory is cleaned up. On the Java side however things are a bit different. A Java object (primitive) is defined as `some_int`. Next a new block of memory `int_p` is allocated on the heap, and the value of `some_int` is copied into it. Because we defined `int_p` inside a try-with-resources, it will be freed (with a call to `close()`) automatically once the try block ends.

TODO
