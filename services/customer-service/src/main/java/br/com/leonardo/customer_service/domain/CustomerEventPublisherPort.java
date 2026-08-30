package br.com.leonardo.customer_service.domain;

public interface CustomerEventPublisherPort {
    void publishCustomerCreated(Customer customer);
    void publishCustomerUpdated(Customer customer);
    void publishCustomerDeleted(Customer customer);
}
