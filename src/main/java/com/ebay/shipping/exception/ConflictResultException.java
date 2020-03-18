package com.ebay.shipping.exception;

/**
 * customized exception for row conflict in creating record
 *
 */
public class ConflictResultException extends Exception {
    public ConflictResultException() {
        super();
    }
    public ConflictResultException(String msg)   {
        super(msg);
    }
    public ConflictResultException(String msg, Exception e)  {
        super(msg, e);
    }
}
