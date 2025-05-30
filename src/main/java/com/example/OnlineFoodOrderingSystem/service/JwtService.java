package com.example.OnlineFoodOrderingSystem.service;

import com.example.OnlineFoodOrderingSystem.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private static  String stringFromSecretKey = null;

    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSha256");
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

        System.out.println();
        System.out.println("in extractClaims method, Claim object "+claims.toString());
        System.out.println();

       return claimResolver.apply(claims);
    }

    private Claims extractAllClaim(String token){
       return Jwts.parser()
                .verifyWith(getKeys())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user){
        return Jwts
                .builder()
                .subject(user.getUserId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ (24 * 60 * 60 * 1000) ))
                .signWith(getKeys())
                .claims()
                .and()
                .compact();
    }

    public boolean validateToken(UserDetails userDetails,String token) {
        if (userDetails.getUsername().equals( extractUsername(token) )) {
            return true;
        }
        return false;
    }
}
