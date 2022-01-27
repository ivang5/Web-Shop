package com.ivang.webshop.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.ivang.webshop.filter.CustomAuthenticationFilter;
import com.ivang.webshop.filter.CustomAuthorizationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/shop/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/shop/login/**", "/shop/users/token/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/shop/users/**").permitAll();
        http.authorizeRequests().antMatchers("/shop/**").hasAnyAuthority("admin");
        http.authorizeRequests().antMatchers("/shop/products/**").hasAnyAuthority("seller");
        http.authorizeRequests().antMatchers("/shop/sales/**").hasAnyAuthority("seller");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/shop/products/**").hasAnyAuthority("buyer");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/shop/sales/**").hasAnyAuthority("buyer");
        http.authorizeRequests().antMatchers("/shop/orders/**").hasAnyAuthority("buyer");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
