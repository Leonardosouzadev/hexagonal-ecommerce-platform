package br.com.leonardo.customer_service.adapters.outbound.persistence;

import br.com.leonardo.customer_service.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerJpaRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    
    @Query("SELECT c FROM Customer c WHERE c.active = true ORDER BY c.createdAt DESC")
    List<Customer> findAllActive();
}
