package br.com.matraca.projetotcc.config;

import br.com.matraca.projetotcc.model.entity.Auth;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.AuthRepository;
import br.com.matraca.projetotcc.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private final AuthRepository authRepository;

    //criação de usuário do google
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(request);

        Boolean isVerified = oAuth2User.getAttribute("email_verified");
        if (!isVerified) {
            throw new RuntimeException("Email não verificado");
        }

        String email = oAuth2User.getAttribute("email");
        String fullname = oAuth2User.getAttribute("name");

        Optional<UserDetails>userOptional = authRepository.findByUser_Email(email);
        if (userOptional.isEmpty()) {
            Auth newAuth = new Auth();
            User newUser = new User();
            newUser.setFullname(fullname);
            newUser.setEmail(email);
            newUser.setCredentials(newAuth);
            newAuth.setUser(newUser);
            newAuth.setEmailVerified(true);
            newAuth.setVerificationToken(null);
            newAuth.setRole(br.com.matraca.projetotcc.model.enums.Role.USER); // ou outro role padrão
            userRepository.save(newUser);
        }

        return oAuth2User; // nesse momento o successHandler pega o JWT
    }
}
