package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.enums.Category;
import peaksoft.enums.Role;
import peaksoft.model.Product;
import peaksoft.model.User;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;

import java.math.BigDecimal;

/**
 * @author Mukhammed Asantegin
 */
@Service
@RequiredArgsConstructor
public class InitialService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void saveData(){

        String encode = passwordEncoder.encode("java12");


        User admin = User.builder()
                .email("admin@gmail.com")
                .password(encode)
                .name("Admin")
                .role(Role.ADMIN)
                .build();

        User client = User.builder()
                .email("aliaskar@gmail.com")
                .password(passwordEncoder.encode("java12"))
                .name("Aliaskar")
                .role(Role.ADMIN)
                .build();



        Product product = Product.builder()
                .name("Odejda")
                .category(Category.MEN)
                .image("http:ksdflsjfdksl;oj3")
                .price(BigDecimal.valueOf(1500))
                .quantity(100)
                .build();

        userRepository.save(admin);
        productRepository.save(product);

    }
}
