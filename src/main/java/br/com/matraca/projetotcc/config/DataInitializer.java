package br.com.matraca.projetotcc.config;

import java.time.LocalDate;
import br.com.matraca.projetotcc.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.matraca.projetotcc.model.enums.Gender;
import br.com.matraca.projetotcc.model.enums.Role;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.AuthRepository;
import br.com.matraca.projetotcc.repository.UserRepository;

@Configuration
public class DataInitializer {

    private final UserService userService;

    DataInitializer(UserService userService) {
        this.userService = userService;
    }

      @Bean
    CommandLineRunner init(AuthRepository authRepository, UserRepository userRepository) {
        return args -> {
            if (authRepository.findByLogin("public").isEmpty()) {
                User user = new User();
                user.setFullname("Usuário Público");
                user.setEmail("public@matraca.com.br");
                user.setPhoneNumber("1234567890");
                user.setGender(Gender.masculino);
                user.setBirthDate(LocalDate.of(2000,01,01));
                userService.registerUser(user,"public@matraca.com.br", "", Role.PUBLIC);
                System.out.println("Usuário público criado com sucesso.");
            }

            if (authRepository.findByLogin("admin").isEmpty()) {
                User user = new User();
                user.setFullname("Administrador");
                user.setEmail("admin@matraca.com.br");
                user.setPhoneNumber("1234567890");
                user.setGender(Gender.masculino);
                user.setBirthDate(LocalDate.of(2000,01,01));
                userService.registerUser(user,"admin@matraca.com.br", "admin123", Role.ADMIN);
                System.out.println("Admin criado com sucesso.");
            }
        };
    }
}
