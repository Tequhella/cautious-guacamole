package com.ynov.m2.advanced_software_development.cautious_guacamole.server.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        // Ignorer certains endpoints
        String path = request.getRequestURI();
        if (path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
            chain.doFilter(request, response);
            return;
        }

        // Récupérer le header Authorization
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String subject = null; // ex: l'email

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                // Parse le token
                Claims claims = jwtUtils.parseToken(token);
                subject = claims.getSubject(); // ex: l'email stocké comme subject
            } catch (Exception e) {
                // Token invalide / expiré
                System.out.println("Erreur token: " + e.getMessage());
            }
        }

        // Si on a bien le subject (email) et pas encore d’authentification
        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Construire un objet d’authentification
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(subject, null, null);

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // Informer Spring Security qu’on est authentifié
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Poursuivre la chaîne
        chain.doFilter(request, response);
    }
}