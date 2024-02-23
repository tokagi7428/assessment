package com.kbtg.bootcamp.posttest.securityConfig;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthFilterTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthFilter authFilter;

    @Test
    public void testDoFilterInternalWithValidCredentials() throws ServletException, IOException {
        // Mocking the request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic " + encodeBase64Credentials("username", "password"));

        // Mocking the response
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Mocking the user returned by the UserService
        UserModel user = new UserModel();
        user.setUsername("username");
        user.setPassword(new BCryptPasswordEncoder().encode("password"));
        user.setRoles(List.of("ROLE_USER"));
        user.setPermissions(List.of("ROLE_USER"));
        when(userService.findByUsername("username")).thenReturn(Optional.of(user));

        // Mocking the FilterChain
        FilterChain filterChain = mock(FilterChain.class);

        // Call the doFilterInternal method
        authFilter.doFilterInternal(request, response, filterChain);

        // Verify that the request is forwarded to the next filter in the chain
        verify(filterChain, times(1)).doFilter(request, response);
        // Verify that authentication is set in SecurityContextHolder
        assert SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken;
    }

    @Test
    public void testDoFilterInternalWithInvalidCredentials() throws ServletException, IOException {
        // Mocking the request with invalid credentials
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic " + encodeBase64Credentials("username", "wrong_password"));

        // Mocking the response
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Mocking the user returned by the UserService
        UserModel user = new UserModel();
        user.setUsername("username");
        user.setPassword(new BCryptPasswordEncoder().encode("password"));
        when(userService.findByUsername("username")).thenReturn(Optional.of(user));

        // Mocking the FilterChain
        FilterChain filterChain = mock(FilterChain.class);

        // Call the doFilterInternal method
        var exception = assertThrows(BadRequestException.class, () -> authFilter.doFilterInternal(request, response, filterChain));

        // Verify that the request is not forwarded to the next filter in the chain
        verify(filterChain, never()).doFilter(request, response);
        assertEquals("username or password is not correct",exception.getMessage());
    }

    private String encodeBase64Credentials(String username, String password) {
        String credentials = username + ":" + password;
        return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}