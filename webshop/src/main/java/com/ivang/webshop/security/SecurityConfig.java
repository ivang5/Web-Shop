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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        http.cors();
        http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/shop/login/**", "/shop/users/token/**").permitAll()
            .antMatchers(HttpMethod.GET, "/shop/users/**").permitAll()
            .antMatchers(HttpMethod.POST, "/shop/users/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/shop/users/admin/**").hasAnyAuthority("admin")
            .antMatchers(HttpMethod.PUT, "/shop/users/seller/**").hasAnyAuthority("admin", "seller")
            .antMatchers(HttpMethod.PUT, "/shop/users/buyer/**").hasAnyAuthority("admin", "buyer")
            .antMatchers(HttpMethod.GET, "/shop/products/**").hasAnyAuthority("admin", "seller", "buyer")
            .antMatchers("/shop/products/**").hasAnyAuthority("admin", "seller")
            .antMatchers(HttpMethod.GET, "/shop/sales/**").hasAnyAuthority("admin", "buyer")
            .antMatchers("/shop/sales/**").hasAnyAuthority("seller")
            .antMatchers(HttpMethod.GET, "/shop/orders/**").hasAnyAuthority("admin", "seller", "buyer")
            .antMatchers(HttpMethod.PUT, "/shop/orders/**").hasAnyAuthority("admin", "seller", "buyer")
            .antMatchers("/shop/orders/**").hasAnyAuthority("admin", "buyer")
            .antMatchers(HttpMethod.GET, "/shop/items/**").hasAnyAuthority("admin", "seller", "buyer")
            .antMatchers("/shop/items/**").hasAnyAuthority("admin", "buyer")
            .antMatchers(HttpMethod.GET, "/shop/files/**").hasAnyAuthority("admin", "seller", "buyer")
            .antMatchers("/shop/files/**").hasAnyAuthority("admin", "seller")
            .antMatchers("/shop/**").hasAnyAuthority("admin")
            .anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
			}
		};
	}

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
