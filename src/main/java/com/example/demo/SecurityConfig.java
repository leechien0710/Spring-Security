package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.models.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired private UserDetailsService userDetailsService;
	@Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/","/img/**","/css/**","/login","/api/**","/signup").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
			).formLogin((f)->f.loginPage("/login")
					.usernameParameter("username")
					.passwordParameter("password").
					defaultSuccessUrl("/")
					)
			.logout((l)->l.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").invalidateHttpSession(true)
					.clearAuthentication(true))
			.csrf().ignoringRequestMatchers("/api/**","/login","/signup","/");
			;
		return http.build();
	}
	@Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
