package com.udemy.jwtdemo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.udemy.jwtdemo.service.CustomUserDetailsService;
import com.udemy.jwtdemo.util.JWTUtil;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		logger.info("JWTAuthenticationFilter");
		logger.info("URL: " + request.getRequestURL().toString());
		
		String bearerToken = request.getHeader("Authorization");
		String username = null;
		String token = null;
		if (bearerToken!=null && bearerToken.startsWith("Bearer ")) {
			token = bearerToken.substring(7);
			logger.info("token: " + token);
			try {
				username = jwtUtil.extractUsername(token);
				logger.info("username: " + username);
				
				// Can this inside "Security checks" ??
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				
				// Security checks
				if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
					
					UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(upat);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			logger.info("Invalid Bearer Token Format!");
		}
		
		filterChain.doFilter(request, response);
	}

}