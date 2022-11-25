package com.epam.exhibitions;

import com.epam.exhibitions.controller.CustomAuthenticationSuccessHandler;
import com.epam.exhibitions.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    //private final UserDetailsServiceImpl userDetailsService;

    // FIXME: Declare the Bean on the class level or here
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/administrator/**").hasAuthority("ROLE_Administrator")
            .antMatchers("/authorized-user/**").hasAuthority("ROLE_Authorized User")
            .antMatchers("/normal-user/**").hasAuthority("ROLE_Normal User")
            .and()
            .csrf().disable()
            .httpBasic();

        http
            .formLogin()
                .permitAll()
                .successHandler(authenticationSuccessHandler());


        http.authenticationProvider(authenticationProvider());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}