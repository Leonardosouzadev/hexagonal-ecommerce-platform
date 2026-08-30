package br.com.leonardo.customer_service.adapters.inbound.rest;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
