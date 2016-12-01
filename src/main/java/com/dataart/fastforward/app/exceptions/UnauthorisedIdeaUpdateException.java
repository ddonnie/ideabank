package com.dataart.fastforward.app.exceptions;

/**
 * Created by logariett on 01.12.2016.
 */
public class UnauthorisedIdeaUpdateException extends RuntimeException {

    public UnauthorisedIdeaUpdateException() {
        super();
    }

    public UnauthorisedIdeaUpdateException(String message) {
        super(message);
    }

    public UnauthorisedIdeaUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
