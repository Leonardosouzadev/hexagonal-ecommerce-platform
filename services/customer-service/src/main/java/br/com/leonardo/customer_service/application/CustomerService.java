package br.com.leonardo.customer_service.application;

import br.com.leonardo.customer_service.adapters.inbound.rest.CustomerNotFoundException;
import br.com.leonardo.customer_service.adapters.inbound.rest.DuplicateCpfException;
import br.com.leonardo.customer_service.adapters.inbound.rest.DuplicateEmailException;
import br.com.leonardo.customer_service.adapters.inbound.rest.InvalidCredentialsException;
import br.com.leonardo.customer_service.application.dto.AddressRequest;
import br.com.leonardo.customer_service.application.dto.CreateCustomerRequest;
import br.com.leonardo.customer_service.domain.Address;
import br.com.leonardo.customer_service.domain.Customer;
import br.com.leonardo.customer_service.domain.CustomerEventPublisherPort;
import br.com.leonardo.customer_service.domain.CustomerRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepositoryPort customerRepository;
    private final CustomerEventPublisherPort customerEventPublisher;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepositoryPort customerRepository,
                          CustomerEventPublisherPort customerEventPublisher,
                          PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerEventPublisher = customerEventPublisher;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Customer register(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException("Email already registered");
        }

        if (customerRepository.existsByCpf(request.cpf())) {
            throw new DuplicateCpfException("CPF already registered");
        }

        Customer customer = new Customer(
                request.email(),
                request.name(),
                passwordEncoder.encode(request.password()),
                request.phone(),
                request.cpf()
        );

        Customer savedCustomer = customerRepository.save(customer);
        customerEventPublisher.publishCustomerCreated(savedCustomer);
        return savedCustomer;
    }

    public Customer findById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void validatePassword(Customer customer, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, customer.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }

    @Transactional
    public Customer addAddress(UUID customerId, AddressRequest request) {
        Customer customer = findById(customerId);
        customer.addAddress(new Address(
                request.street(),
                request.number(),
                request.complement(),
                request.city(),
                request.state(),
                request.zipCode(),
                request.country()
        ));

        Customer updatedCustomer = customerRepository.save(customer);
        customerEventPublisher.publishCustomerUpdated(updatedCustomer);
        return updatedCustomer;
    }

    @Transactional
    public Customer updateProfile(UUID customerId, String name, String phone) {
        Customer customer = findById(customerId);
        customer.updateName(name);
        customer.updatePhone(phone);

        Customer updatedCustomer = customerRepository.save(customer);
        customerEventPublisher.publishCustomerUpdated(updatedCustomer);
        return updatedCustomer;
    }

    @Transactional
    public void deleteCustomer(UUID customerId) {
        Customer customer = findById(customerId);
        customer.deactivate();
        customerRepository.save(customer);
        customerEventPublisher.publishCustomerDeleted(customer);
    }
}
