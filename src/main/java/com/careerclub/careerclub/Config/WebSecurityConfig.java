package com.careerclub.careerclub.Config;

import com.careerclub.careerclub.Middlewares.Jwtfilter;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final Jwtfilter jwtfilter;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, Jwtfilter jwtfilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtfilter = jwtfilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/code/**").hasAnyAuthority("admin","otp")
                .antMatchers("/roles/**").hasAuthority("admin")
                .antMatchers("/auth/login","/users/**").permitAll()
                .antMatchers("/auth/refresh-token").hasAnyAuthority("admin","member","hr")
                .anyRequest().permitAll().and()
                .addFilterBefore(jwtfilter, BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic();
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
