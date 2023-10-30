package com.attrecto.academy.java.courseapp.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.attrecto.academy.java.courseapp.persistence.UserRepository;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private UserRepository userRepository;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
	
	@Bean
	public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception { 			
		return  http.csrf(csrfConfigurer -> csrfConfigurer.disable())
					.cors(CorsConfigurer<HttpSecurity>::disable)
					.httpBasic(HttpBasicConfigurer<HttpSecurity>::disable)
					.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.NEVER))
					.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
					.authorizeHttpRequests(auth -> auth
							.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS, "**")).permitAll()
							.requestMatchers(getExcludedAntEndpoints()).permitAll()
							.anyRequest().authenticated())
					.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
					.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	    return (web) -> web.ignoring().requestMatchers(getExcludedAntEndpoints());
	}
	
    @Bean
    public WebMvcConfigurer configure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry reg) {
                reg.addMapping("/**").allowedOrigins("http://localhost:4000")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "TRACE", "PATCH");
            }
        };
    }
	
	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> logFilter() {
	    FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
	    registrationBean.setFilter(new JwtAuthenticationFilter(userRepository));
	    registrationBean.addUrlPatterns("/api/courses/**", "/api/users/**");
	    return registrationBean;
	}

	// dummy implementation to suppress some default Spring Security configuration
	@Bean
	public UserDetailsService dummyUserDetailsService() {
		return username -> null;
	}
	
	private AntPathRequestMatcher[] getExcludedAntEndpoints() {
		return Arrays.asList(JwtAuthenticationFilter.EXCLUDED_ENDPOINTS).stream().map(endpoint -> new AntPathRequestMatcher(endpoint)).toArray(AntPathRequestMatcher[]::new);
	}
}
