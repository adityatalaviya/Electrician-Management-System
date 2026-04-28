package com.electrician.electrician_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter ;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity){
         httpSecurity
                .csrf(c->c.disable())
                 .cors(c->{}) //enable cors

                .authorizeHttpRequests(auth->auth
                        //------------------- cors-----------------
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()

                        //--------------Admin----------------------
                        .requestMatchers("/admin/login","/admin/create").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        //-------------Electrician-----------------
                        .requestMatchers("/electrician/request-otp", "/electrician/verify-otp").permitAll()
                        .requestMatchers("/electrician/**").hasRole("ELECTRICIAN")

                        //------------Worker------------------
                        //------------Worker------------------
                        .requestMatchers("/worker/request-otp","/worker/verify-otp").permitAll()
                        .requestMatchers("/worker/**").hasRole("WORKER")

                        //----------------Other-----------
                        .anyRequest().authenticated())

                 //stateless JWT
                 .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                 //add JWT Filter
                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);



                return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration authenticationConfiguration ){
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
