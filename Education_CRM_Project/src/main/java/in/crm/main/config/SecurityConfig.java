package in.crm.main.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import in.crm.main.handler.CustomSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	//private JwtUtils jwtUtil;
	private UserDetailsService userDetailService;
	
	private CustomSuccessHandler customSuccessHandler;
	
	
	
	public SecurityConfig(UserDetailsService userDetailsService, CustomSuccessHandler customSuccessHandler) {
		//this.jwtUtil = jwtUtil;
		this.userDetailService = userDetailsService;
		this.customSuccessHandler = customSuccessHandler;
	}
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return  new ProviderManager(Arrays.asList(daoAuthenticationProvider()));
			
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
//		http
//        .authorizeHttpRequests(auth -> auth
//            .requestMatchers("/adminLogin", "/css/**", "/js/**").permitAll()
//            .anyRequest().authenticated()
//        )
//        .formLogin(form -> form
//            .loginPage("/adminLogin")   // your custom login URL
//            .loginProcessingUrl("/adminLogin")// form action
//            .defaultSuccessUrl("/adminProfile", true)
//            .failureUrl("/login?error=true")
//            .permitAll()
//        )
//        .logout(logout -> logout
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/login?logout=true")
//            .permitAll()
//        );
//		
//		return http.build();
		
		
		http.
		authorizeHttpRequests(auth-> auth
				.requestMatchers("/login","/css/**","/js/**")
				.permitAll()
				.requestMatchers("/adminProfile**").hasRole("ADMIN")
				.requestMatchers("/employeeManagement**").hasAnyRole("ADMIN","EMPLOYEE")
				.requestMatchers("/userProfile**").hasRole("STUDENT")
				.anyRequest().authenticated())
		.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.successHandler(customSuccessHandler).permitAll()
				) 
		.logout(logout -> logout
		                .logoutSuccessUrl("/login?logout=true")
			            );
		
		http.csrf(csrf -> csrf.disable());

			        return http.build();

		
	}
}
