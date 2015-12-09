Intro
=====

Playground for what is supposed to be a library similar to BridJ or JNA.

Goals are:
 - Simple usage.
 - Simple runtime API.
 - No config files.
 - Only C.
 - Linux only initially.
 - Support for Win/Lin/Mac - x86, x86_64, armsf, armhf.
 - Support for all common use cases: unions, callbacks, pointer-to-pointer, ...
 - Compile time annotation processor gathers all statically inferable information and generates runtime API independent code.

Pointer API
==========

Jaccall's tries not to be Java, but instead tries to bring C to the world of Java.
This means:
 - No automatic garbage collection. What you allocate, you must free yourself.
 - Cast to and from anything to anything.
 - Read to and from anything to anything.

This approach is reflected in the Pointer API. Take the following example:
```Java
import static com.github.zubnix.jaccall.Pointer.*
...
try(Pointer<Integer> intRef = malloc(sizeof((Integer)null).castp(Integer.class)){
  LibFoo.bar(intRef.address);
}
...
```

TODO
