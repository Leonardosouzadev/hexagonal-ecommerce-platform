package br.com.leonardo.customer_service.application;

import br.com.leonardo.customer_service.adapters.inbound.rest.CustomerNotFoundException;
import br.com.leonardo.customer_service.adapters.inbound.rest.DuplicateCpfException;
import br.com.leonardo.customer_service.adapters.inbound.rest.DuplicateEmailException;
import br.com.leonardo.customer_service.application.dto.CreateCustomerRequest;
import br.com.leonardo.customer_service.application.dto.AddressRequest;
import br.com.leonardo.customer_service.domain.Customer;
import br.com.leonardo.customer_service.domain.Address;
import br.com.leonardo.customer_service.domain.CustomerRepositoryPort;
import br.com.leonardo.customer_service.domain.CustomerEventPublisherPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService Tests")
class CustomerServiceTest {

    @Mock
    private CustomerRepositoryPort customerRepository;

    @Mock
    private CustomerEventPublisherPort customerEventPublisher;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    private CreateCustomerRequest validRequest;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        validRequest = new CreateCustomerRequest(
                "test@example.com",
                "John Doe",
                "password123",
                "(11) 99999-9999",
                "123.456.789-00"
        );

        testCustomer = new Customer(
                "test@example.com",
                "John Doe",
                "hashedpassword",
                "(11) 99999-9999",
                "123.456.789-00"
        );
    }

    @Test
    @DisplayName("Should register customer successfully")
    void testRegisterSuccess() {
        when(customerRepository.existsByEmail(anyString())).thenReturn(false);
        when(customerRepository.existsByCpf(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedpassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        Customer result = customerService.register(validRequest);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(customerRepository).save(any(Customer.class));
        verify(customerEventPublisher).publishCustomerCreated(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void testRegisterDuplicateEmail() {
        when(customerRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> customerService.register(validRequest));
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when CPF already exists")
    void testRegisterDuplicateCpf() {
        when(customerRepository.existsByEmail(anyString())).thenReturn(false);
        when(customerRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(DuplicateCpfException.class, () -> customerService.register(validRequest));
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find customer by ID")
    void testFindById() {
        UUID customerId = UUID.randomUUID();
        testCustomer.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testCustomer));

        Customer result = customerService.findById(customerId);

        assertNotNull(result);
        assertEquals(customerId, result.getId());
    }

    @Test
    @DisplayName("Should throw exception when customer not found by ID")
    void testFindByIdNotFound() {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(customerId));
    }

    @Test
    @DisplayName("Should find customer by email")
    void testFindByEmail() {
        when(customerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testCustomer));

        Customer result = customerService.findByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    @DisplayName("Should add address to customer")
    void testAddAddress() {
        UUID customerId = UUID.randomUUID();
        testCustomer.setId(customerId);

        AddressRequest addressRequest = new AddressRequest(
                "Rua Test",
                "123",
                "Apt 1",
                "São Paulo",
                "SP",
                "01310-100",
                "Brasil"
        );

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        Customer result = customerService.addAddress(customerId, addressRequest);

        assertNotNull(result);
        assertTrue(result.getAddresses().size() > 0);
    }

    @Test
    @DisplayName("Should update customer profile")
    void testUpdateProfile() {
        UUID customerId = UUID.randomUUID();
        testCustomer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        Customer result = customerService.updateProfile(customerId, "Jane Doe", "(11) 88888-8888");

        assertNotNull(result);
        verify(customerRepository).save(any(Customer.class));
        verify(customerEventPublisher).publishCustomerUpdated(any(Customer.class));
    }

    @Test
    @DisplayName("Should delete customer (soft delete)")
    void testDeleteCustomer() {
        UUID customerId = UUID.randomUUID();
        testCustomer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        customerService.deleteCustomer(customerId);

        verify(customerRepository).save(any(Customer.class));
        verify(customerEventPublisher).publishCustomerDeleted(any(Customer.class));
    }
}
