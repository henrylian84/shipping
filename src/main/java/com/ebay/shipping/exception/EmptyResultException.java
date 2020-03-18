package com.ebay.shipping.exception;

/**
 * customized exception for deleting the record that doesn't exist
 *
 */
public class EmptyResultException extends Exception {

    public EmptyResultException() {
        super();
    }
    public EmptyResultException(String msg)   {
        super(msg);
    }
    public EmptyResultException(String msg, Exception e)  {
        super(msg, e);
    }
}
