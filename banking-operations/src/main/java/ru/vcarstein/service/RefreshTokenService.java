package ru.vcarstein.service;


import ru.vcarstein.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);

    Optional<RefreshToken> findByToken(String token);

    boolean validateRefreshToken(String token);

    String getUsernameFromToken(String token);

    void deleteByUsername(String username);
}
