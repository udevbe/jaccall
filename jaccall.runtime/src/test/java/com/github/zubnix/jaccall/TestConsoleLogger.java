package com.github.zubnix.jaccall;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TestConsoleLogger extends RunListener {
    @Override
    public void testStarted(final Description description) throws Exception {
        System.err.println("Test started: "+description);
    }

    @Override
    public void testFinished(final Description description) throws Exception {
        System.err.println("Test finished: "+description);
    }

    @Override
    public void testAssumptionFailure(final Failure failure) {
        System.err.println("Test assumption failure: "+failure);
    }

    @Override
    public void testFailure(final Failure failure) throws Exception {
        System.err.println("Test failure: "+failure);
    }
}
