package com.senyint.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableWebSecurity
// 通过@EnableWebMvcSecurity注解开启Spring Security的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	// 继承WebSecurityConfigurerAdapter，并重写它的方法来设置一些web安全的细节

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//onfigureGlobal(AuthenticationManagerBuilder auth)方法，在内存中创建了一个用户，该用户的名称为user，密码为password，用户角色为USER。
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("USER");
		auth.inMemoryAuthentication().withUser("test").password("test").roles("TEST");
		
//		auth.jdbcAuthentication().dataSource(dataSource);// 设置为从数据库读取用户认证信息(JdbcUserdetailsService)
	}

	/**
	 * configure(HttpSecurity http)方法
	 * 通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护。
	 * 例如以上代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。
	 * 通过formLogin()定义当需要用户登录时候，转到的登录页面。
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/hello","/test").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").permitAll().and().logout().permitAll();
//		http.anonymous().disable().authorizeRequests().anyRequest().authenticated();
	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/hystrix.stream/**", "/info", "/error");
//	}
}