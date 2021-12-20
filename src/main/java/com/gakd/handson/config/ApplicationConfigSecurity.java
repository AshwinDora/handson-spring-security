package com.gakd.handson.config;

import static com.gakd.handson.config.ApplicationUserRole.ADMIN;
import static com.gakd.handson.config.ApplicationUserRole.ADMIN_TRAINEE;
import static com.gakd.handson.config.ApplicationUserRole.STUDENT;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/index.*", "/css/*", "/js/*").permitAll()
                .antMatchers("/management/**").hasRole(ADMIN_TRAINEE.name())
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }*/

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/index.*", "/css/*", "/js/*").permitAll()
                .antMatchers(HttpMethod.DELETE,"/management/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.POST,"/management/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers("/management/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }*/

  /*  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/index.*", "/css/*", "/js/*").permitAll()
                *//*.antMatchers(HttpMethod.DELETE,"/management/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.POST,"/management/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers("/management/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
                .antMatchers("/api/**").hasRole(STUDENT.name())*//*
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/index.*", "/css/*", "/js/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses").permitAll()
                .and()
                .headers().frameOptions().sameOrigin();  // this has nothing to do with loading external iframes.
                                                         // this only tells if any external application can load our app based of iframe settings.
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        UserDetails userDetails = User.builder()
                .username("Anna")
                .password(passwordEncoder.encode("password"))
                .authorities(STUDENT.getGrantedAuthorities())
               // .roles(STUDENT.name())
                .build();

        UserDetails lindaUserDetails = User.builder()
                .username("Linda")
                .password(passwordEncoder.encode("password123"))
                .authorities(ADMIN.getGrantedAuthorities())
               // .roles(ADMIN.name())
                .build();

        UserDetails tomUserDetails = User.builder()
                .username("Tom")
                .password(passwordEncoder.encode("password123"))
                .authorities(ADMIN_TRAINEE.getGrantedAuthorities())
                //.roles(ADMIN_TRAINEE.name())
                .build();

        List<UserDetails> userDetailsList = Arrays.asList(userDetails, lindaUserDetails, tomUserDetails);

        return new InMemoryUserDetailsManager(userDetailsList);
    }
}
