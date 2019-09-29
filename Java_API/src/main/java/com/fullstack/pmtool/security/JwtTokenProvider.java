package com.fullstack.pmtool.security;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fullstack.pmtool.domain.User;
import io.jsonwebtoken.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.fullstack.pmtool.security.SecurityConstants.EXIPIRATION_TIME;
import static com.fullstack.pmtool.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    //Generate the token

    public String generateToken(Authentication authentication){
        User user=(User) authentication.getPrincipal();
        Date now =new Date(System.currentTimeMillis());

        Date expiryDate= new Date(now.getTime()+EXIPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String,Object>  claims=new HashMap<>( );
        claims.put("id",(Long.toString(user.getId())));
        claims.put("username",user.getUsername());
        claims.put("fullname",user.getFullname());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
    }
    //Validate the token

    public boolean validateToken(String token){
        try{

                Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
                return  true;
        }catch (SignatureException ex ){
            System.out.println("Invalid JWT signature");

        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");
        }catch(UnsupportedJwtException ex){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            System.out.println("Jwt claims String is Empty");
        }
        return  false;
    }

    //GEt the user id from the token.

    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id =(String) claims.get("id");

        return Long.parseLong(id);

    }
}
