package com.ivang.webshop.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivang.webshop.utility.SecurityHelper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String requestString = "";
        String username = "";
        String password = "";

        try {
            requestString = request.getReader().readLine();
            username = getTruncatedString(requestString, 3, 4);
            password = getTruncatedString(requestString, 7, 8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Username is: {}", username);
        log.info("Password is: {}", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        User user = (User)authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        List<String> authorities = user.getAuthorities().stream().map(ga -> ga.getAuthority()).collect(Collectors.toList());
        Map<String, String> tokens = SecurityHelper.getTokens(request, user.getUsername(), authorities, algorithm);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    private String getTruncatedString(String text, int startQuote, int endQuote) {
        int counter = 0;
        int startIndex = 0;
        int endIntex = 0;
        boolean startFound = false;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '"') {
                counter++;
            }

            if (counter == startQuote && startFound == false) {
                startIndex = i + 1;
                startFound = true;
            } else if (counter == endQuote) {
                endIntex = i;
                break;
            }
        }

        String truncatedString = text.substring(startIndex, Math.min(text.length(), endIntex));
        
        return truncatedString;
    }
}
