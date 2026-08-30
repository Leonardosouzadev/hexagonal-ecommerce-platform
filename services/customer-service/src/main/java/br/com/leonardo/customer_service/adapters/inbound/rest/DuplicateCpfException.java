package br.com.leonardo.customer_service.adapters.inbound.rest;

public class DuplicateCpfException extends RuntimeException {
    public DuplicateCpfException(String message) {
        super(message);
    }
}
