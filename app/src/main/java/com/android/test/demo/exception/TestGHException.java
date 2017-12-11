package com.android.test.demo.exception;

/**
 * des:
 * author: libingyan
 * Date: 17-12-8 17:52
 */
public class TestGHException extends RuntimeException {

    public TestGHException(Throwable cause) {
        super(cause);
    }

    public TestGHException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public TestGHException(String message) {
        super(message);
    }

    public TestGHException(String message, Throwable cause) {
        super(message, cause);
    }
}
