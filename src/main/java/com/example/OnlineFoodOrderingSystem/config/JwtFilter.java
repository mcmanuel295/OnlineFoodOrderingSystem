package com.example.OnlineFoodOrderingSystem.config;

import com.example.OnlineFoodOrderingSystem.model.User;
import com.example.OnlineFoodOrderingSystem.sevice.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Configuration
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token=null;

        if (header!=null && header.startsWith("Bearer ")){
            token = header.substring(7);

            try {
                final String secretString = "esiuhfwueiofwqbyiwexiowqeovgyw8oecnoiwebif" ;
                SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes());
                Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

                System.out.println();
                System.out.println("in the doFilterInternal method, the Claim object "+claims.toString());
                System.out.println();

                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
            }
            catch (Exception ex){
                throw new BadCredentialsException("Invalid token.......");
            }
        }

        String username = jwtService.extractUsername(token);


//        verify the header
        if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
            User user = new CustomUserDetailService().loadUserByUsername(username);

            if(jwtService.validateToken()){
                UsernamePasswordAuthenticationToken upaAToken = new UsernamePasswordAuthenticationToken(ema)
                SecurityContextHolder.getContext().setAuthentication();

            }
        }
        filterChain.doFilter(request,response);
    }
}
