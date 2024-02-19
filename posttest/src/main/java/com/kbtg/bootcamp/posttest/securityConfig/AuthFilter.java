package com.kbtg.bootcamp.posttest.securityConfig;


import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final UserService userService;

    public AuthFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String password = null;
        if(authorizationHeader != null){
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();

            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String decodedCredentials = new String(decodedBytes, StandardCharsets.UTF_8);

            String[] credentialsArray = decodedCredentials.split(":", 2);
            if (credentialsArray.length == 2) {
//            System.out.println( credentialsArray[0]); // username
//            System.out.println( credentialsArray[1]); // password
                username = credentialsArray[0];
                password = credentialsArray[1];
            }
        }
        Optional<UserModel>  user = userService.findByUsername(username);
        System.out.println(authorizationHeader);
        if(user.isPresent() && authorizationHeader != null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean encodedPassword = encoder.matches(password,user.get().getPassword());
            System.out.println(encodedPassword);
            if(!encodedPassword){
                throw new BadRequestException("username or password is not correct");
            }
        }

        if(username != null && !username.equals("") && SecurityContextHolder.getContext().getAuthentication() == null){

            List<GrantedAuthority> authorities = new ArrayList<>(user.get().getAuthorities());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request,response);


    }


}

