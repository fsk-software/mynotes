package com.fsk.common.threads;


/**
 * An exception that documents an error with the thread.
 */
public class ThreadException extends RuntimeException {

    /**
     * Constructor.
     */
    public ThreadException () {
        super();
    }


    /**
     * Constructor.
     *
     * @param detailMessage the detail message for this exception.
     */
    public ThreadException (String detailMessage) {
        super(detailMessage);
    }


    /**
     * Constructor.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable the cause of this exception.
     */
    public ThreadException (String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }


    /**
     * Constructor.
     *
     * @param throwable the cause of this exception.
     */
    public ThreadException (Throwable throwable) {
        super(throwable);
    }
}
