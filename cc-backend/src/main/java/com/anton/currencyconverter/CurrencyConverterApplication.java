package com.anton.currencyconverter;

import com.anton.currencyconverter.domain.entity.Role;
import com.anton.currencyconverter.domain.entity.User;
import com.anton.currencyconverter.domain.repository.RoleRepository;
import com.anton.currencyconverter.domain.repository.UserRepository;
import com.anton.currencyconverter.service.api.CurrencyService;
import com.anton.currencyconverter.service.api.RateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CurrencyConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner setUp(CurrencyService currencyService,
                            RateService rateService,
                            UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByName("USER").orElse(roleRepository.save(new Role(null, "USER")));
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            User root = userRepository.findByUsername("root")
                    .orElse(userRepository.save(new User(null, "root", passwordEncoder.encode("123"), roles)));

            currencyService.loadStartCurrencyData();
            rateService.loadDailyRateData();
        };
    }
}
