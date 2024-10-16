package es.udc.paproject.backend.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtGenerator jwtGenerator;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.addFilter(new JwtFilter(authenticationManager(), jwtGenerator))
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/users/signUp").permitAll()
			.antMatchers(HttpMethod.POST, "/users/login").permitAll()
			.antMatchers(HttpMethod.POST, "/users/loginFromServiceToken").permitAll()
			.antMatchers(HttpMethod.PUT, "/users/*").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/users/*/changePassword").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/products").hasRole("USER")				//[FUNC-1] addProduct
			.antMatchers(HttpMethod.GET, "/products/products/*").permitAll()			//[FUNC-3] findProductById
			.antMatchers(HttpMethod.GET, "/products/user").hasRole("USER")			//[FUNC-6] findProductsByUserId
			.antMatchers(HttpMethod.POST, "/bid").hasRole("USER")					//[FUNC-4] bidding
			.antMatchers(HttpMethod.GET, "/products/products").permitAll()
			.antMatchers(HttpMethod.GET, "/products/categories").permitAll()
			.antMatchers(HttpMethod.GET, "/bid/user").hasRole("USER")
			.anyRequest().denyAll();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		config.setAllowCredentials(true);
	    config.setAllowedOriginPatterns(Arrays.asList("*"));
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");

	    source.registerCorsConfiguration("/**", config);

	    return source;

	 }

}
