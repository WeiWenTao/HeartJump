package com.cucr.myapplication.utils.throwableCatch;

/**
 * Created by wanjian on 2017/2/15.
 */

final class QuitCockroachException extends RuntimeException {
    public QuitCockroachException(String message) {
        super(message);
    }
}
