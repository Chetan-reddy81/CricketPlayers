package com.cricket.project.configurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cricket.project.filters.JwtAuthFilter;
import com.cricket.project.serviceimpl.UserDetailsServiceImpl;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	
	@Autowired
	JwtAuthFilter authFilter;
	
	@Bean
	public UserDetailsService  userDetailsService()
	{
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//@Bean
	/*public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http.cors().and().csrf().disable().
		
			//authorizeHttpRequests().requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN").and().
			//authorizeHttpRequests().requestMatchers("/user/**").hasAuthority("ROLE_USER").and().
				
			//authorizeHttpRequests().requestMatchers("/common/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER").and().
				//authorizeHttpRequests().requestMatchers("/any/**").permitAll().and().

			authorizeHttpRequests().requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN").and().
			authorizeHttpRequests().requestMatchers("/user/**").hasAuthority("ROLE_USER").and().
				
			authorizeHttpRequests().requestMatchers("/common/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER").and().
				authorizeHttpRequests().requestMatchers("/","/**").permitAll().and().
				 
	          
				sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
	}*/
	@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf().disable()
            .authorizeHttpRequests()
            .anyRequest().permitAll()
            .and()
            .build();
}

	
	@Bean 
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
	  return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		 
		return config.getAuthenticationManager();
	}
}
