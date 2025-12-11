package myTunes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // fine for testing with Postman / REST
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // allow everything for now
                );
        return http.build();
    }
}
