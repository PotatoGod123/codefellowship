package com.potatogod123.codefellowship.security.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity// look at this file, configure it for security

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImplementation userDetailsServiceImplementation;

    //password encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //configure for auth

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override//this is called by spring to load our user detail service, and same for OUR password encoder
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
                authenticationManagerBuilder.userDetailsService(userDetailsServiceImplementation)
                .passwordEncoder(passwordEncoder());//from line 22 encoder we made
    }

    //configure for httpAccess

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //enable or disable built in middleware
                .cors().disable()
                .csrf().disable()

                //authorization stuff
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/signup","/login","/application-user").permitAll()
                .anyRequest().authenticated()


                .and()
                .formLogin()
                .defaultSuccessUrl("/userinfo")
                .loginPage("/login")


                .and()
                .logout();


    }
}
