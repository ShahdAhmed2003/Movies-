package com.movieflix.movieApi.controllers;

import com.movieflix.movieApi.auth.entities.RefreshToken;
import com.movieflix.movieApi.auth.entities.User;
import com.movieflix.movieApi.auth.services.AuthService;
import com.movieflix.movieApi.auth.services.JwtService;
import com.movieflix.movieApi.auth.services.RefreshTokenService;
import com.movieflix.movieApi.auth.utils.AuthResponse;
import com.movieflix.movieApi.auth.utils.LoginRequest;
import com.movieflix.movieApi.auth.utils.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService,RefreshTokenService refreshTokenService,JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService=refreshTokenService;
        this.jwtService=jwtService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse>register(@RequestBody RegisterRequest registerRequest)
    {
       return ResponseEntity.ok(authService.register(registerRequest)) ;
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse>register(@RequestBody LoginRequest loginRequest)
    {
        return ResponseEntity.ok(authService.login(loginRequest)) ;
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse>refreshToken(@RequestBody RefreshToken refreshTokenrequest)
    {
        RefreshToken refreshToken=refreshTokenService.verifyRefreshToken(refreshTokenrequest.getRefreshToken());
        User user=refreshToken.getUser();
        String accessToken= jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(accessToken,refreshToken.getRefreshToken()));

    }
}
