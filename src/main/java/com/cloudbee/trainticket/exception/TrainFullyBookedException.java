package com.cloudbee.trainticket.exception;

public class TrainFullyBookedException extends RuntimeException{

    public TrainFullyBookedException(String message) {
        super(message);
    }
}
