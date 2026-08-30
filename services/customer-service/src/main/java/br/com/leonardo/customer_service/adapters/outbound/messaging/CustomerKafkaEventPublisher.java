package br.com.leonardo.customer_service.adapters.outbound.messaging;

import br.com.leonardo.customer_service.domain.Customer;
import br.com.leonardo.customer_service.domain.CustomerEventPublisherPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerKafkaEventPublisher implements CustomerEventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(CustomerKafkaEventPublisher.class);

    @Override
    public void publishCustomerCreated(Customer customer) {
        log.info("Customer created event: {}", customer.getEmail());
    }

    @Override
    public void publishCustomerUpdated(Customer customer) {
        log.info("Customer updated event: {}", customer.getEmail());
    }

    @Override
    public void publishCustomerDeleted(Customer customer) {
        log.info("Customer deleted event: {}", customer.getEmail());
    }
}
