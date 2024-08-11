package com.in28minutes.rest.webservices.restful_web_services.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //all requests should be authenticated
        http.authorizeHttpRequests(
                auth-> auth.anyRequest().authenticated()
        );
        //if req is not authenticated, web page is shown
        http.httpBasic(withDefaults());

        //CSRF-->POST,PUT
        http.csrf(csrf->csrf.disable());
        return http.build();

    }

}
