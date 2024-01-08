package com.mintyn.codingtest.Configuration.security;

import com.mintyn.codingtest.Configuration.exception.MintynException;
import com.mintyn.codingtest.model.dao.UserDao;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component @RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDao userDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final var header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.isBlank() || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        final var token = header.split(" ")[1].trim();
        final var username = jwtUtils.extractUsername(token);
        if (!jwtUtils.validateToken(token, username)) {
            chain.doFilter(request, response);
            return;
        }

        var user = userDao.findByUsername(username).orElseThrow(() -> new MintynException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                null
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
