/**
 * Project Name:auth2
 * File Name:WebSecurityConfig.java
 * Package Name:com.briup.apps.auth2.config
 * Date:2018年9月17日上午10:23:44
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.config;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.DigestUtils;
import org.springframework.web.cors.CorsUtils;

import com.briup.apps.poll.service.impl.UserService;
import com.briup.apps.poll.util.MsgResponse;

import net.sf.json.JSONObject;

/**
 * ClassName:WebSecurityConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年9月17日 上午10:23:44 <br/>
 * 
 * @author lichunyu
 * @version
 * @since JDK 1.6
 * @see
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
			}

			/**
			 * @param charSequence
			 *            明文
			 * @param s
			 *            密文
			 * @return
			 */
			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return s.equals(DigestUtils.md5DigestAsHex(charSequence.toString().getBytes()));
			}
		});
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.anyRequest()
		.permitAll()
		.and().csrf().disable();
		
		/*
		
		http.authorizeRequests()
		.antMatchers("/manager/**").authenticated()
		.antMatchers("/manager/**").hasAnyRole("超级管理员","班主任")/// admin/**的URL都需要有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")
		.antMatchers("/teacher/**").authenticated()
		.antMatchers("/teacher/**").hasAnyRole("超级管理员","普通用户","班主任")
		.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
		.anyRequest().authenticated()// 其他的路径都是登录后即可访问
		.and().formLogin()
		.loginPage("/login_page")
		.successHandler(new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse, Authentication authentication)
					throws IOException, ServletException {
				httpServletResponse.setContentType("application/json;charset=utf-8");
				PrintWriter out = httpServletResponse.getWriter();
				UserDetails userDetails 
					= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				MsgResponse msg = MsgResponse.success("登录成功", userDetails);
				out.write(JSONObject.fromObject(msg).toString());
				//回显用户信息
				out.flush();
				out.close();
			}
		})
		.failureHandler(new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse, AuthenticationException e)
					throws IOException, ServletException {
				httpServletResponse.setContentType("application/json;charset=utf-8");
				PrintWriter out = httpServletResponse.getWriter();
				JSONObject obj =new JSONObject();
				obj.put("status", 500);
				obj.put("message", e.getMessage());
				out.write(JSONObject.fromObject(obj).toString());
				out.flush();
				out.close();
			}
		})
		.loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password").permitAll()
		.and().logout().permitAll()
		.and().csrf().disable().exceptionHandling()
		.accessDeniedHandler(getAccessDeniedHandler())
		.and().cors();	
		*/
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/blogimg/**", "/index.html", "/static/**",
				"/swagger-ui.html","/webjars/**","/swagger-resources/**","/v2/**","/index/**","/student/**");
	}

	@Bean
	AccessDeniedHandler getAccessDeniedHandler() {
		return new AuthenticationAccessDeniedHandler();
	}
}
