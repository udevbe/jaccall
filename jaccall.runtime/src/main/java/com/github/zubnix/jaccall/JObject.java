package com.github.zubnix.jaccall;


public final class JObject implements AutoCloseable {

    private final Object pojo;
    public final  long   address;

    public JObject(final Object pojo) {
        this.pojo = pojo;
        this.address = JNI.NewGlobalRef(this);
    }

    public Object pojo() {
        return this.pojo;
    }

    @Override
    public void close() throws Exception {
        JNI.DeleteGlobalRef(this);
    }
}
