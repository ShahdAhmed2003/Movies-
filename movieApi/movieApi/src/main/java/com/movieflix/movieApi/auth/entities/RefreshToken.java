package com.movieflix.movieApi.auth.entities;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;
    @Column(nullable = false,length = 500)
    @NotBlank(message = "Please enter refresh token value")
    private String refreshToken;
    @Column(nullable = false)
    private Instant expirationTime;
    @OneToOne
    private User user;

    public RefreshToken() {
    }

    public RefreshToken(Integer tokenId, String refreshToken, Instant expirationTime, User user) {
        this.tokenId = tokenId;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
        this.user = user;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setExpirationTime(Instant expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public User getUser() {
        return user;
    }
}
