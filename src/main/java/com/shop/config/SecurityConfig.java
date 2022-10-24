package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//인증
		http.formLogin()
			.loginPage("/members/login")
			.defaultSuccessUrl("/")
		    .usernameParameter("email")
		    .failureUrl("/members/login/error")
		    .and()
		    .logout()
		    .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
		    .logoutSuccessUrl("/");
		
		//권한
		http.authorizeRequests()
			.antMatchers("/", "/members/**").permitAll()   //인증되지 않은 모든 사용자 접근
			.antMatchers("/admin/**").hasRole("ADMIN");   //ADMIN 권한을 가진 사용자만 접근
	}
	
	@Bean
	public PasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	
	
}
