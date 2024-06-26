package ca.cmpt276.examharmony;

import ca.cmpt276.examharmony.Model.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//Configures Spring Security for application
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public CustomUserDetailService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests( //Configure authorizations and permissions for users
                        authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")  //Users with role ADMIN can only access "/admin/.." urls
                        .requestMatchers("/instructor/**").hasRole("INSTRUCTOR")
                        .requestMatchers("/invigilator/**").hasRole("INVIGILATOR")
                        .anyRequest().authenticated()   //All other users must log in
                )
                .formLogin(withDefaults())  //TODO: Implement custom login page
                .httpBasic(withDefaults());
        return http.build();
    }
}
