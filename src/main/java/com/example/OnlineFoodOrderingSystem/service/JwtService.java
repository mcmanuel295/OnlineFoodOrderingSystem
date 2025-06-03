package com.example.OnlineFoodOrderingSystem.service;

import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    private final UserRepository userRepo;
    private static  String stringFromSecretKey = null;

    public JwtService(UserRepository userRepository) throws NoSuchAlgorithmException {
        userRepo = userRepository;
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = keyGen.generateKey();
        stringFromSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public SecretKey getKeys(){
       byte[] keyBytes =Base64.getDecoder().decode(stringFromSecretKey);
       return Keys.hmacShaKeyFor(keyBytes);
   }

    public String extractUsername(String token) {
       return  extractClaim(token,Claims::getSubject);
    }

    private  <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claims = extractAllClaim(token);

       return claimResolver.apply(claims);
    }

    private Claims extractAllClaim(String token){
       return Jwts.parser()
                .verifyWith(getKeys())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(Authentication authentication){
        Map<String,Object> claims = new HashMap<>();
        User user = userRepo.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ (24 * 60 * 60 * 1000) ))
                .and()
                .signWith(getKeys())
                .compact();
    }

    public boolean validateToken(UserDetails userDetails,String token) {
        return (userDetails.getUsername().equals( extractUsername(token)) )&& !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).after(new Date());
    }
}
