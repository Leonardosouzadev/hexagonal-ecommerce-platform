package br.com.leonardo.customer_service.application;

import br.com.leonardo.customer_service.adapters.inbound.rest.InvalidCredentialsException;
import br.com.leonardo.customer_service.application.dto.LoginRequest;
import br.com.leonardo.customer_service.application.dto.LoginResponse;
import br.com.leonardo.customer_service.config.JwtUtil;
import br.com.leonardo.customer_service.domain.Customer;
import br.com.leonardo.customer_service.domain.CustomerRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CustomerRepositoryPort customerRepository;
    private final CustomerService customerService;
    private final JwtUtil jwtUtil;

    public AuthService(CustomerRepositoryPort customerRepository,
                       CustomerService customerService,
                       JwtUtil jwtUtil) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        Customer customer = customerService.findByEmail(request.email());
        
        if (!customer.isActive()) {
            throw new InvalidCredentialsException("Account is inactive");
        }

        customerService.validatePassword(customer, request.password());

        String accessToken = jwtUtil.generateToken(customer.getEmail(), customer.getId().toString());
        String refreshToken = jwtUtil.generateRefreshToken(customer.getEmail(), customer.getId().toString());

        return new LoginResponse(accessToken, 86400000L);
    }

    public LoginResponse refreshToken(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new InvalidCredentialsException("Invalid or expired refresh token");
        }

        String email = jwtUtil.extractEmail(token);
        String userId = jwtUtil.extractUserId(token);
        
        String newAccessToken = jwtUtil.generateToken(email, userId);

        return new LoginResponse(newAccessToken, 86400000L);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token);
    }

    public String extractEmail(String token) {
        return jwtUtil.extractEmail(token);
    }
}
