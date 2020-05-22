package com.stevenkolamkuzhiyil.TicTacToe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/user/profile**").authenticated()
                .antMatchers("/api**", "/admin**").hasRole("ADMIN")
                .antMatchers("/user/profile**", "/game**").authenticated()
                .antMatchers("/user/login**", "/user/signIn**", "/user/register**").anonymous()
                .antMatchers("/", "/index**", "/game/index", "/error**").permitAll()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/user/signIn")
                .successHandler(authenticationSuccessHandler())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessHandler(authenticationSuccessHandler())
                .logoutSuccessUrl("/user/login")
                .and()
                .rememberMe()
                .rememberMeParameter("checkRememberMe")
                .tokenValiditySeconds(86400);
    }

    @Bean
    protected DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    protected AuthSuccessHandlerWithSessionHandler authenticationSuccessHandler() {
        return new AuthSuccessHandlerWithSessionHandler();
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
