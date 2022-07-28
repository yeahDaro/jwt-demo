package com.udemy.jwtdemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.udemy.jwtdemo.bean.CustomUserDetails;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
	
	Logger logger = LoggerFactory.getLogger(JWTUtil.class);

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	public String extractUsername(String token) {
		logger.info("extractUsername");
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		logger.info("isTokenExpired");
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(CustomUserDetails userDetails) {
		logger.info("generateToken");
		Map<String, Object> claims = new HashMap<>();
		claims.put("profile", userDetails.getProfile());
		claims.put("permissions", userDetails.getAuthorities());
		return createToken(claims, userDetails.getUsername());
	}

	// long extramilis = 1000 * 60 * 60 * 10;
	// 1000 = 1s
	//        1s * 60 = 1m
	//                  1m * 60 = 1h
	//                            1h * 10 = 10h
	private String createToken(Map<String, Object> claims, String subject) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		long now = System.currentTimeMillis();
		long extramilis = 1000 * 60 * 60 * 10;
		Date issuedAt = new Date(now);
		Date expiration = new Date(now + extramilis);
		logger.info("issuedAt: " + sdf.format(issuedAt));
		logger.info("expiration: " + sdf.format(expiration));
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(issuedAt)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		logger.info("validateToken");
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}