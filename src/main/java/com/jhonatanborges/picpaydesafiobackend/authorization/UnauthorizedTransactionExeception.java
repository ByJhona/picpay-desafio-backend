package com.jhonatanborges.picpaydesafiobackend.authorization;

public class UnauthorizedTransactionExeception extends RuntimeException {
    private String message;
    public UnauthorizedTransactionExeception(String message) {
        this.message = message;
    }
}
