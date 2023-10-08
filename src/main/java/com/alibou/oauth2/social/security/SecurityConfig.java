package com.alibou.oauth2.social.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

		http.csrf(csrf -> csrf.disable())
//				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//       Customize the below code to your need. For further information you can visit https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-securityfilterchain
				.authorizeHttpRequests(
          auth -> auth.requestMatchers("/app/auth/api/**").permitAll().anyRequest().authenticated()
				);
		
    http.authenticationProvider(authenticationProvider);
    
		http.logout(logout -> {
			logout.logoutUrl("/app/logout");
			logout.addLogoutHandler(logoutHandler);
			logout.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
		});
		return http.build();
  }
}

