package com.fsk.common.threads;


/**
 * An exception that documents an error with the thread.
 */
class ThreadException : RuntimeException {

    /**
     * Basic Constructor.
     */
    constructor () : super();

    /**
     * Constructor.
     *
     * @param detailMessage the detail message for this exception.
     */
    constructor (detailMessage: String?) : super(detailMessage);


    /**
     * Constructor.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable the cause of this exception.
     */
    constructor (detailMessage: String?,
                 throwable: Throwable?) : super(detailMessage, throwable);


    /**
     * Constructor.
     *
     * @param throwable the cause of this exception.
     */
    constructor (throwable: Throwable?) : super(throwable);
}
