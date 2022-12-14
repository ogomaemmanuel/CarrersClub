package com.careerclub.careerclub.Middlewares;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class Jwtfilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var headerValue = request.getHeader("Authorization");
        if(headerValue != null && headerValue.startsWith("Bearer ")){
            var token = headerValue.substring(7);
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            try{
                var jwt = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                var rolesClaimString= jwt.getBody().get("roles");
                var authorities= new ArrayList<SimpleGrantedAuthority>();
                if(rolesClaimString!=null){
                    authorities = (ArrayList<SimpleGrantedAuthority>) Arrays.stream(rolesClaimString.toString().split(";")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                }
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(jwt.getBody().getSubject(),null,authorities);
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authToken);
                SecurityContextHolder.setContext(securityContext);
            }catch (Exception e){
                response.setStatus(401);
                response.getWriter().println("Access denied");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
}
