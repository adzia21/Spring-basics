package com.ltp.contacts.exception;

public class ContactNotFoundExcepiton extends RuntimeException {

    public ContactNotFoundExcepiton(String id) {
        super("The id '" + id + "' does not exist in our records");
    }
}
