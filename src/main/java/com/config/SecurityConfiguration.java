package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import javax.sql.DataSource;

@Configuration("SecurityConfiguration")
@EnableWebSecurity
@Order(101) // Little hack to trick Spring
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource securityDataSource;

    @Autowired
    public SecurityConfiguration(DataSource securityDataSource) {
        this.securityDataSource = securityDataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(securityDataSource);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and().csrf().disable()
                .authorizeRequests().anyRequest().permitAll()
                .and().sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // secures all REST endpoints under "/api/customers"
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/api/customers/").authenticated()
//                .antMatchers(HttpMethod.GET,"/api/customers/*").authenticated()
//                .antMatchers(HttpMethod.POST,"/api/customers/").hasAnyRole("MANAGER","ADMIN")
//                .antMatchers(HttpMethod.PUT,"/api/customers/").hasAnyRole("MANAGER","ADMIN")
//                .antMatchers(HttpMethod.DELETE,"/api/customers/*").hasRole("ADMIN")
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }



}