package com.genius.tech.library.initialData;

import com.genius.tech.library.enums.Role;
import com.genius.tech.library.models.User;
import com.genius.tech.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AdminDataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.name:Super Admin}")
    private String adminName;

    @Value("${app.bootstrap.admin.email:admin@geniustech.local}")
    private String adminEmail;

    @Value("${app.bootstrap.admin.password:admin123}")
    private String adminPassword;

    public AdminDataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.countByRole(Role.ADMIN) > 0) {
            return;
        }

        String normalizedEmail = adminEmail.trim().toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            System.out.println("Admin seeding skipped: email already exists but belongs to non-admin user -> " + normalizedEmail);
            return;
        }

        User admin = new User(
                "ADM-001",
                adminName.trim(),
                normalizedEmail,
                passwordEncoder.encode(adminPassword),
                Role.ADMIN,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        userRepository.save(admin);
        System.out.println("Default admin created: " + normalizedEmail);
    }
}
