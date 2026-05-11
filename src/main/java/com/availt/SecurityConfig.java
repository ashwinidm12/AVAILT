package com.availt;

import com.availt.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .ignoringAntMatchers("/api/**", "/h2-console/**", "/logout")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeHttpRequests()
                .antMatchers(
                        "/css/**",
                        "/login",
                        "/signup",
                        "/",
                        "/categories",
                        "/services",
                        "/service/**",
                        "/join-provider",
                        "/h2-console/**",
                        "/api/categories",
                        "/api/services/**",
                        "/api/menus/**"
                ).permitAll()
                .antMatchers(HttpMethod.POST, "/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/join-provider").permitAll()
                .antMatchers("/menu/**", "/book/**", "/summary", "/confirmation").authenticated()
                .antMatchers(HttpMethod.POST, "/api/bookings").authenticated()
                .antMatchers(HttpMethod.GET, "/api/bookings/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .userDetailsService(userDetailsService);

        return http.build();
    }
}
