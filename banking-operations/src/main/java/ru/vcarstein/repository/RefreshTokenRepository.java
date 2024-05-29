package ru.vcarstein.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vcarstein.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
