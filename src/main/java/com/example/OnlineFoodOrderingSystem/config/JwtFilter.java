package com.example.OnlineFoodOrderingSystem.config;

import com.example.OnlineFoodOrderingSystem.service.CustomUserDetailService;
import com.example.OnlineFoodOrderingSystem.service.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token=null;
        String username =null;


        if (header!=null && header.startsWith("Bearer ")){
            token = header.substring(7);
            username = jwtService.extractUsername(token);

            System.out.println("the String for username in jwtFilter is "+username);



            //        verify the header
            if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                if(jwtService.validateToken(userDetails,token)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
