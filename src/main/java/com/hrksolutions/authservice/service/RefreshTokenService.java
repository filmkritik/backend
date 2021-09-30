package com.hrksolutions.authservice.service;

import java.time.Instant;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hrksolutions.authservice.dto.UserDto;
import com.hrksolutions.authservice.entities.RefreshTokenEntity;
import com.hrksolutions.authservice.entities.UserEntity;
import com.hrksolutions.authservice.exception.TokenRefreshException;
import com.hrksolutions.authservice.repository.RefreshTokenRepository;
import com.hrksolutions.authservice.utilities.JwtTokenUtil;


@Service
public class RefreshTokenService {

	private static final Logger logger = Logger.getLogger(RefreshTokenService.class);
	
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${app.token.refresh.duration}")
    private Long refreshTokenDurationMs;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Find a refresh token based on the natural id i.e the token itself
     */
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Persist the updated refreshToken instance to database
     */
    public RefreshTokenEntity save(RefreshTokenEntity refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Creates and returns a new refresh token
     */
    public RefreshTokenEntity createRefreshToken() {
    	RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(JwtTokenUtil.generateRandomUuid());
        refreshToken.setRefreshCount(0L);
        return refreshToken;
    }

    /**
     * Verify whether the token provided has expired or not on the basis of the current
     * server time and/or throw error otherwise
     */
    public void verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
        	logger.error("Expired refresh token");
            throw new TokenRefreshException(token.getToken(), "Expired token. Please login again");
        }
    }

    /**
     * Delete the refresh token associated with the user device
     */
    public void deleteById(UserEntity userEntity) {
        refreshTokenRepository.deleteByUserEntity(userEntity);
    }

    /**
     * Increase the count of the token usage in the database. Useful for
     * audit purposes
     */
    public void increaseCount(RefreshTokenEntity refreshToken) {
        refreshToken.incrementRefreshCount();
        save(refreshToken);
    }

	public boolean isUserIdPresent(UserEntity currentUser) {
		return refreshTokenRepository.existsByUserEntity(currentUser);
	}
}
