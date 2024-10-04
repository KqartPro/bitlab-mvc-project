package kz.pryahin.SpringBootTwo.configs;

import kz.pryahin.SpringBootTwo.services.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Bean
	public ClientService clientService() {
		return new ClientService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder =
			http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder
			.userDetailsService(clientService())
			.passwordEncoder(passwordEncoder());
		http.formLogin(formLogin -> formLogin
			.loginPage("/car/login")
			.loginProcessingUrl("/login")
			.usernameParameter("user-email")
			.passwordParameter("user-password")
			.defaultSuccessUrl("/car/home")
			.failureUrl("/car/login?error")
		);
		http.logout(logout -> logout
			.logoutUrl("/logout")
			.logoutSuccessUrl("/car/login")
		);

		http.csrf(csrf -> csrf.disable());
		return http.build();
	}
}
