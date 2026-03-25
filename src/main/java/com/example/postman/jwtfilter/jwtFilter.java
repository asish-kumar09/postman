package com.example.postman.jwtfilter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.postman.TokenUtil;

import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class jwtFilter extends OncePerRequestFilter{
	@Autowired
	private TokenUtil tokenutil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException{
		String header=request.getHeader("Authorization");
		if(header!=null && header.startsWith("Bearer ")) {
			String token=header.substring(7);
			Claims claims=tokenutil.validateTokens(token);
			if(claims!=null) {
				String username=claims.getSubject();
				String role=(String)claims.get("role");
				List<GrantedAuthority> authorities=List.of(new SimpleGrantedAuthority("ROLE_"+role));
				UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(username,null,authorities);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		filterChain.doFilter(request,response);
	}
}
