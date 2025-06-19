package br.com.matraca.projetotcc.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.matraca.projetotcc.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.recoverToken(request);
        if (token != null) {
            try {
                var login = tokenService.validateToken(token);
                UserDetails user = authService.loadUserByUsername(login);
                var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                Cookie cookie = new Cookie("JWT_TOKEN", "");
                cookie.setHttpOnly(true);
                cookie.setSecure(true); // variável para produção
                cookie.setPath("/");
                cookie.setMaxAge(0);
                
                SecurityContextHolder.clearContext();
                response.addCookie(cookie);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                addCorsHeaders(response, request);
                response.getWriter().write("Erro ao validar token: " + e.getMessage());
                return;
            }
        } else {
            // Usuário público (anônimo)
            UserDetails publicUser = authService.loadUserByUsername("public@matraca.com.br");
            var publicAuth = new UsernamePasswordAuthenticationToken(publicUser, null, publicUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(publicAuth);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
    // Primeiro tenta pegar do cookie
    if (request.getCookies() != null) {
        for (var cookie : request.getCookies()) {
            System.out.println("cookie: " + cookie.getName());
            if ("JWT_TOKEN".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
    }

    // Se não tiver cookie, tenta Authorization header
    var authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        return authorizationHeader.replace("Bearer ", "");
    }

    return null;
    }


    private void addCorsHeaders(HttpServletResponse response, HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        if (origin != null && List.of(
                "http://localhost:3000",
                "http://localhost:3001",
                "https://matraca.onrender.com"
        ).contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
    }

}
