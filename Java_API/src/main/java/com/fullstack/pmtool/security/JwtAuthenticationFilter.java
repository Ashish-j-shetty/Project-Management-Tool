package com.fullstack.pmtool.security;

import com.fullstack.pmtool.domain.User;
import com.fullstack.pmtool.services.CustomUserDetailsService;
import javassist.bytecode.stackmap.BasicBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.fullstack.pmtool.security.SecurityConstants.HEADER_STRING;
import static com.fullstack.pmtool.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try{
            String jwt = getJWtFromReuest(request);
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
                Long userId =  tokenProvider.getUserIdFromJWT(jwt);
                User userDetails= customUserDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken  authentication =new UsernamePasswordAuthenticationToken(
                        userDetails,null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        } catch (Exception ex){
            logger.error("Could not set user Authentication in security context",ex);
        }

        filterChain.doFilter(request,response);

    }

    private String getJWtFromReuest(HttpServletRequest request){
        String bearerToken = request.getHeader(HEADER_STRING);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)){
            return  bearerToken.substring(7,bearerToken.length());
        }
        return  null;
    }
}