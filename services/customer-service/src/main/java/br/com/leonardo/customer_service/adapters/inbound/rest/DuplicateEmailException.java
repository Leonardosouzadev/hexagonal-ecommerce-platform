package br.com.leonardo.customer_service.adapters.inbound.rest;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
