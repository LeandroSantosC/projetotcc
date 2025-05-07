package br.com.matraca.projetotcc.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.matraca.projetotcc.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
            var login = tokenService.validateToken(token);
            System.out.println(login);
            if (login != null) {
                try {
                    UserDetails user = authService.loadUserByUsername(login);
                    var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Erro ao validar usuário: " + e.getMessage());
                    return;
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
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
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
