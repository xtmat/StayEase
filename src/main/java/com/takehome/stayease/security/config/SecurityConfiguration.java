package com.takehome.stayease.security.config;

import com.takehome.stayease.security.model.Role;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/api/auth/**",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final DaoAuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req
                                .antMatchers(WHITE_LIST_URL).permitAll()
                                // Hotel Controller
                                .antMatchers(HttpMethod.GET, "/api/hotels/**").permitAll()//hasAnyAuthority(Role.CUSTOMER.name(), Role.HOTEL_MANAGER.name(), Role.ADMIN.name())
                                // Authentication Controller
                                .antMatchers(HttpMethod.POST, "/api/users/register", "/api/users/refresh-token", "/api/users/login").permitAll()
                                .antMatchers(HttpMethod.POST, "/api/hotels").hasAuthority(Role.ADMIN.name())
                                //allow admins to delete hotel
                                .antMatchers(HttpMethod.DELETE, "/api/hotels").hasAuthority(Role.ADMIN.name())
                                //allow manager to update hotels
                                .antMatchers(HttpMethod.PUT, "/api/hotels/**").hasAuthority(Role.HOTEL_MANAGER.name())
                                  // Booking Controller
                                .antMatchers(HttpMethod.DELETE, "/api/bookings/{id}").hasAnyAuthority(Role.HOTEL_MANAGER.name(), Role.ADMIN.name())
                                 // allow users to book hotels
                                .antMatchers(HttpMethod.POST, "/api/bookings/**").hasAnyAuthority(Role.HOTEL_MANAGER.name(), Role.ADMIN.name(), Role.CUSTOMER.name())
                                //allow users to get hotels based on the availabe rooms
                                .antMatchers(HttpMethod.GET, "/api/bookings/**").hasAnyAuthority(Role.HOTEL_MANAGER.name(), Role.ADMIN.name(), Role.CUSTOMER.name())
                                // User Controller
                                .antMatchers(HttpMethod.GET, "/api/users/me").hasAnyAuthority(Role.CUSTOMER.name(), Role.HOTEL_MANAGER.name(), Role.ADMIN.name())
                                // All other endpoints require ADMIN authority
                                .anyRequest().hasAuthority(Role.ADMIN.name())
                )
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .addFilterBefore(new OncePerRequestFilter() {
                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                            throws ServletException, IOException {
                        log.debug("Processing request: {} {}", request.getMethod(), request.getRequestURI());
                        filterChain.doFilter(request, response);
                    }
                }, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
