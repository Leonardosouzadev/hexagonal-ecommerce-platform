package br.com.leonardo.customer_service.adapters.inbound.rest;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
