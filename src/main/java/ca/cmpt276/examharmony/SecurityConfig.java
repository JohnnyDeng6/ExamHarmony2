package ca.cmpt276.examharmony;

import ca.cmpt276.examharmony.Model.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests( //Configure authorizations and permissions for users
                        authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")  //Users with role ADMIN can only access "/admin/.." urls
                        .requestMatchers("/instructor/**").hasRole("INSTRUCTOR")
                        .requestMatchers("/invigilator/**").hasRole("INVIGILATOR")
                                .requestMatchers("/test/**").permitAll()
                        .anyRequest().authenticated()   //All other users must log in
                )
                .formLogin(form->form.loginPage("/login")
                        .permitAll())
                .httpBasic(withDefaults());
        return http.build();
    }
}
