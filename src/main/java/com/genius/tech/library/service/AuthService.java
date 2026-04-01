package com.genius.tech.library.service;

import com.genius.tech.library.dto.request.LoginRequest;
import com.genius.tech.library.dto.response.AuthUserResponse;
import com.genius.tech.library.dto.response.LoginResponse;
import com.genius.tech.library.models.Student;
import com.genius.tech.library.models.User;
import com.genius.tech.library.repository.StudentRepository;
import com.genius.tech.library.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       StudentRepository studentRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new DisabledException("Your account is inactive");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        Student student = studentRepository.findByUserId(user.getId()).orElse(null);
        Long studentId = student != null ? student.getId() : null;
        String gender = student != null && student.getGender() != null ? student.getGender().name() : null;
        String seatSection = student != null && student.getSeatSection() != null ? student.getSeatSection().name() : null;
        String seatNumber = student != null ? student.getSeatNumber() : null;
        BigDecimal monthlyFee = student != null ? student.getMonthlyFee() : null;
        AuthUserResponse authUser = new AuthUserResponse(
                user.getId(),
                studentId,
                user.getUserCode(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getIsActive(),
                gender,
                seatSection,
                seatNumber,
                monthlyFee
        );

        // Placeholder token for frontend session flow.
        String token = "dev-" + UUID.randomUUID();

        return new LoginResponse(token, authUser);
    }
}
