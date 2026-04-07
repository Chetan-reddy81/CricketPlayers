package com.cricket.project.serviceimpl;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtService {

	
	
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	 public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }

	 private Claims extractAllClaims(String token) {
	        return Jwts
	                .parserBuilder()
	                .setSigningKey(getSignKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }
	 public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }
	 private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	       
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	        
	        
	        }
	 
//	public String generateToken(String userName) {
//		Map<String,Object> claims=new HashMap<>();
//		return createToken(claims,userName);
//	}

	private String createToken(Map<String, Object> claims, String userName,Collection<? extends GrantedAuthority> userRoles) {
		// TODO Auto-generated method stub
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
		
				.claim("roles", userRoles)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
	            .signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		// TODO Auto-generated method stub
		byte[] keyBytes=Decoders.BASE64.decode("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");
	    return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
		// TODO Auto-generated method stub
		Map<String,Object> claims=new HashMap<>();
		
		return createToken(claims,username,authorities);
	}
	
	public String getCurrentUserName() {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken)) {
			String currentUserName=auth.getName();
			return currentUserName;
		}
		return null;
	}
	
}
