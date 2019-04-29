package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//store details in RAM- Temp memory
		auth.inMemoryAuthentication().withUser("ajay").password(pwdEncoder.encode("ajay")).authorities("ADMIN");
		auth.inMemoryAuthentication().withUser("sam").password(pwdEncoder.encode("sam")).authorities("EMP");
		auth.inMemoryAuthentication().withUser("khan").password(pwdEncoder.encode("khan")).authorities("STD");
	}
	
	
	public void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/all").permitAll()
		.antMatchers("/emp").hasAuthority("EMP")
		.antMatchers("/admin").hasAnyAuthority("ADMIN")
		.antMatchers("/common").hasAnyAuthority("ADMIN","EMP")
		.anyRequest().authenticated()
		
		
		//show the login form
		.and()
		.formLogin()
		.defaultSuccessUrl("/view", true)
		
		//provide logout links
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		
		//display Denied Page
		.and()
		.exceptionHandling()
		.accessDeniedPage("/denied")
		
		;
	}
	
	
	
	
	
}
