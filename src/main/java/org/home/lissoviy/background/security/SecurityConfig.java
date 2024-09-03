package org.home.lissoviy.background.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security config api, use only basic authorization.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Username from application config.
   */
  @Value("${app.security.username}")
  private String username;

  /**
   * Password from application config.
   */
  @Value("${app.security.password}")
  private String password;

  /**
   * Configures basic authorization in the application.
   *
   * @param http object to configure the security settings of the web application.
   * @return The security filter chain used by Spring Security to handle HTTP requests.
   * @throws Exception if csrf throw Exception.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authManagerRequestMatcherRegistry -> authManagerRequestMatcherRegistry
                .anyRequest()
                .authenticated())
        .httpBasic(withDefaults());
    return http.build();
  }

  /**
   * Creates a user account based on the values specified in the username and password variables.
   *
   * @return user account.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user =
        User.builder()
            .username(username)
            .password(passwordEncoder().encode(password))
            .roles("USER")
            .build();
    return new InMemoryUserDetailsManager(user);
  }

  /**
   * Returns a BCryptPasswordEncoder instance used to encrypt passwords.
   *
   * @return BCryptPasswordEncoder instance used to encrypt passwords
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
