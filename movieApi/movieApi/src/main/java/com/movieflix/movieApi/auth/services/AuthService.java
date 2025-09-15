package com.movieflix.movieApi.auth.services;
import com.movieflix.movieApi.auth.entities.RefreshToken;
import com.movieflix.movieApi.auth.entities.User;
import com.movieflix.movieApi.auth.entities.UserRole;
import com.movieflix.movieApi.auth.repositories.UserRepository;
import com.movieflix.movieApi.auth.utils.AuthResponse;
import com.movieflix.movieApi.auth.utils.LoginRequest;
import com.movieflix.movieApi.auth.utils.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository,JwtService jwtService,RefreshTokenService refreshTokenService,AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService=jwtService;
        this.refreshTokenService=refreshTokenService;
        this.authenticationManager=authenticationManager;
    }

    public AuthResponse register(RegisterRequest registerRequest)
    {
        User user=new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRole.USER);
        User savedUser=userRepository.save(user);
        String accessToken=jwtService.generateToken(savedUser);
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(savedUser.getEmail());
        return new AuthResponse(accessToken,refreshToken.getRefreshToken());




    }
    public AuthResponse login(LoginRequest loginRequest)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new UsernameNotFoundException("User name Not found Exception"));
        String accessToken=jwtService.generateToken(user);
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(loginRequest.getEmail());
        return new AuthResponse(accessToken,refreshToken.getRefreshToken());

    }
}
