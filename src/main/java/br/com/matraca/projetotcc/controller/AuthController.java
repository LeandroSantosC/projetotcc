package br.com.matraca.projetotcc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.smartcardio.Card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.matraca.projetotcc.config.TokenService;
import br.com.matraca.projetotcc.dto.ApiResponse;
import br.com.matraca.projetotcc.dto.LoginDTO;
import br.com.matraca.projetotcc.dto.RegisterDTO;
import br.com.matraca.projetotcc.model.entity.Auth;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.enums.Role;
import br.com.matraca.projetotcc.repository.AuthRepository;
import br.com.matraca.projetotcc.repository.UserRepository;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDTO auth, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(auth.login(), auth.password());
        var authentication = this.authenticationManager.authenticate(usernamePassword);
        Auth authUser = (Auth) authentication.getPrincipal();
        if (authUser.getRole() == Role.USER && !authUser.isEmailVerified()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Email not verified"));
        }

        var token = tokenService.generateToken(authUser.getLogin());
  
        // Cria cookie com token JWT
        var cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // true em produção com HTTPS
        cookie.setPath("/");
        if(auth.rememberMe()){
            cookie.setMaxAge(60 * 60 * 24 * 30);
        }
        response.addCookie(cookie);

        return ResponseEntity.ok(ApiResponse.success("Login successful " + token));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response) {
  
        // Cria cookie com token JWT
        var cookie = new Cookie("JWT_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // true em produção com HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok(ApiResponse.success("Logout successful"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody @Valid RegisterDTO data) {

            User publicUser = userRepository.getByCredentials_Role(Role.PUBLIC)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Public user not found"));

            User user = new User();

            List<Button> newCards = publicUser.getButton().stream()
                .map(c -> new Button(c.getName(), c.getImage(), c.getSound(), user, c.getCategory()))
                .collect(Collectors.toList());

            user.setFullname(data.fullname());
            user.setEmail(data.email());
            user.setPhoneNumber(data.phoneNumber());
            user.setBirthDate(data.birthDate());
            user.setGender(data.gender());
            user.setButton(newCards);
            userService.registerUser(user, data.email(), data.password(), Role.USER);

        return ResponseEntity.ok(ApiResponse.success("User registered successfully"));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam("token") String token) {
        Auth auth = authRepository.findByVerificationToken(token)
            .orElseThrow(() -> new RuntimeException("Token inválido"));
        auth.setEmailVerified(true);
        auth.setVerificationToken(null);
        authRepository.save(auth);
        return ResponseEntity.ok(ApiResponse.success("Email verificado com sucesso"));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/user")
    public ResponseEntity<ApiResponse<User>> updateUser(@AuthenticationPrincipal Auth auth, @RequestBody User updates) {
        User user = auth.getUser();
        User updatedUser = userService.updateUser(user, updates);

        return ResponseEntity.ok(ApiResponse.success(updatedUser));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<User>> getUser(@AuthenticationPrincipal Auth auth) {
        User user = auth.getUser();

        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/user")
    public ResponseEntity<ApiResponse<String>> deleteUser(@AuthenticationPrincipal Auth auth) {
        User user = auth.getUser();
        userService.deleteUser(user);

        return ResponseEntity.ok(ApiResponse.success("Usuário: " + user.getEmail() + " deletado com sucesso!"));
    }
    
}
