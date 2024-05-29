package ru.vcarstein.service.impl;

import org.springframework.stereotype.Service;
import ru.vcarstein.auth.JwtUtil;
import ru.vcarstein.entity.RefreshToken;
import ru.vcarstein.repository.RefreshTokenRepository;
import ru.vcarstein.service.RefreshTokenService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return findByToken(token).map(rt -> !rt.isExpired()).orElse(false);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return findByToken(token).map(RefreshToken::getUsername).orElse(null);
    }

    @Override
    public void deleteByUsername(String username) {
        refreshTokenRepository.deleteAll(
                refreshTokenRepository.findAll().stream()
                        .filter(rt -> rt.getUsername().equals(username))
                        .collect(Collectors.toList())
        );
    }
}
