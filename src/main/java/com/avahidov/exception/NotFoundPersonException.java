package com.avahidov.exception;


public class NotFoundPersonException extends RuntimeException {

    private final String id;

    public NotFoundPersonException(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
