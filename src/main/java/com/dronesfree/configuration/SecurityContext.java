package com.dronesfree.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;

import com.dronesfree.security.service.RepositoryUserDetailsService;
import com.dronesfree.security.service.SimpleSocialUserDetailsService;
import com.dronesfree.user.service.IUserService;

@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private IUserService userService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/* Configures login form */
		http.formLogin().loginPage("/login")
				.loginProcessingUrl("/login/authenticate")
				.defaultSuccessUrl("/")
				.failureUrl("/login?error=bad_credentials")
				.usernameParameter("username").passwordParameter("password");

		/* Configures url based authorization */
		http.authorizeRequests().antMatchers("/", "/auth/**", "/login")
				.permitAll().anyRequest().authenticated().and().httpBasic();

		// disable csrf, add access denied page .
		http.csrf().disable().exceptionHandling().accessDeniedPage("/403");

		/* Logout settings */
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/login")
				.deleteCookies("JSESSIONID");

		/* Adds the SocialAuthenticationFilter to Spring Security's */
		// .and()
		// .apply(new SpringSocialConfigurer());

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		// Spring Security ignores request to static resources such as CSS or JS
		// files.
		.ignoring().antMatchers("/static/**");
	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SocialUserDetailsService socialUserDetailsService() {
		return new SimpleSocialUserDetailsService(userDetailsService());
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new RepositoryUserDetailsService();
	}
}