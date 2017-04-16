package com.senyint.config;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author hpym365
 * @version 创建时间：2017年4月13日 下午9:33:15 类说明
 */
@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class SsoConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Primary
	public OAuth2ClientContextFilter dynamicOauth2ClientContextFilter() {
		return new DynamicOauth2ClientContextFilter();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//需要单点登录的路径
//		http.authorizeRequests().antMatchers("/app/**").hasRole("USER").anyRequest().authenticated().and()
////			 .antMatcher("/ll/**").authorizeRequests().anyRequest().authenticated().and()
//		.csrf().csrfTokenRepository(csrfTokenRepository()).and().addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
//				.logout().logoutUrl("/test/logout").permitAll().logoutSuccessUrl("/");
		http.authorizeRequests().antMatchers("/uaa/**", "/login").permitAll().anyRequest().authenticated()
				.and()
				.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher()).csrfTokenRepository(csrfTokenRepository())
				.and()
				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
				.logout().permitAll()
				.logoutSuccessUrl("/");
		System.out.println("123");
	}

	private RequestMatcher csrfRequestMatcher() {
		return new RequestMatcher() {
			// Always allow the HTTP GET method
			private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|OPTIONS|TRACE)$");

			// Disable CSFR protection on the following urls:
			private final AntPathRequestMatcher[] requestMatchers = { new AntPathRequestMatcher("/uaa/**") };

			@Override
			public boolean matches(HttpServletRequest request) {
				if (allowedMethods.matcher(request.getMethod()).matches()) {
					return false;
				}

				for (AntPathRequestMatcher matcher : requestMatchers) {
					if (matcher.matches(request)) {
						return false;
					}
				}
				return true;
			}
		};
	}

	/**
	 * 
	 * @author hpym365
	 * @创建时间 2017年4月13日 下午9:34:59
	 * @desc Csrt防伪造token
	 * @return
	 */
	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (csrf != null) {
					Cookie cookie = new Cookie("XSRF-TOKEN", csrf.getToken());
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
