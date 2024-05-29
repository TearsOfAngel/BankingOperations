package ru.vcarstein.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vcarstein.auth.JwtUtil;
import ru.vcarstein.dto.AuthRequest;
import ru.vcarstein.dto.AuthResponse;
import ru.vcarstein.dto.TokenRefreshRequest;
import ru.vcarstein.entity.RefreshToken;
import ru.vcarstein.service.CustomUserDetailsService;
import ru.vcarstein.service.RefreshTokenService;

@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication with JWT")
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @Operation(summary = "Login", description = "Login with username and password")
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt, refreshToken.getToken()));
    }

    @Operation(summary = "Refresh token", description = "Return new access token")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request, HttpServletResponse response) {
        String refreshTokenStr = request.getRefreshToken();
        if (refreshTokenService.validateRefreshToken(refreshTokenStr)) {
            String username = refreshTokenService.getUsernameFromToken(refreshTokenStr);
            String newJwt = jwtUtil.generateToken(username);

            Cookie cookie = new Cookie("jwt", newJwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok(new AuthResponse(newJwt, refreshTokenStr));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
        }
    }
}
