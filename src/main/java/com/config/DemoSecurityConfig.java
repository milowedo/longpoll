package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import javax.sql.DataSource;

//@Configuration
//class SessionConfig{
//    @Bean
//    public WebSessionIdResolver webSessionIdResolver(){
//        HeaderWebSessionIdResolver resolver = new HeaderWebSessionIdResolver();
//        System.out.println("e" + resolver);
//        resolver.setHeaderName("X-AUTH-TOKEN");
//        return resolver;
//    }
//}


@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource securityDataSource;

    @Autowired
    public DemoSecurityConfig(DataSource securityDataSource) {
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

        // Why disable CSRF?
        //
        // Spring Security 5 has CSRF enabled by default. You would need to send over CSRF tokens.
        // However, CSRF generally does not apply for REST APIs. CSRF protection is a request that could be processed by a browser by normal users.
        // If you are only creating a REST service that is used by non-browser clients, you will likely want to disable CSRF protection.
        //
        // For more details, see this link:
        // http://www.tothenew.com/blog/fortifying-your-rest-api-using-spring-security/

        // Why disable sessions?
        //
        // For our application, we would like avoid the use of cookies for sesson tracking. This should force the REST client
        // to enter user name and password for each request. However, this is not always the case depending on the REST client / browser
        // you are using. Your mileage will vary here (for example, this doesn't work in Eclipse embedded browser).
        //
        // For more details, see this link
        // http://www.baeldung.com/spring-security-session

    }



}