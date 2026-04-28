package com.electrician.electrician_management.config;

import com.electrician.electrician_management.service.Impl.CustomeUserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil ;

    @Autowired
    private CustomeUserDetailsServiceImpl customeUserDetailsService ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = header.substring(7);
        try {

            String username = jwtUtil.getEmailFromToken(token);
            String role = jwtUtil.getRole(token);

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                if ("ADMIN".equals(role)) {

                    // Admin authentication using UserDetailsService
                    var userDetails = customeUserDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                } else if ("ELECTRICIAN".equals(role)) {

                    // Electrician authentication without UserDetailsService
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_ELECTRICIAN"))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }else if ("WORKER".equals(role)) {

                    Long workerId = jwtUtil.getId(token);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    workerId,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_WORKER"))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (Exception e) {
            System.out.println("JWT Error: " + e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
}
