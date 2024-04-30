package com.agenda.Contactos.Tienda.config.security;

import com.agenda.Contactos.Tienda.persistence.entities.User;
import com.agenda.Contactos.Tienda.persistence.repositories.UserRepository;
import com.agenda.Contactos.Tienda.services.IJwtUtilityService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Optional;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtUtilityService jwtUtilityService;
    private final UserRepository userRepository;
    public JwtAuthenticationFilter(IJwtUtilityService jwtUtilityService, UserRepository userRepository){
        this.jwtUtilityService = jwtUtilityService;
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = header.substring(7);
        try {
            JWTClaimsSet claims = jwtUtilityService.parseJwt(token);
            System.out.printf(claims.getSubject());
            Optional<User> userOptional = this.userRepository.findByEmail(claims.getSubject());
            User user = userOptional.get();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(),null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request,response);
    }
}
