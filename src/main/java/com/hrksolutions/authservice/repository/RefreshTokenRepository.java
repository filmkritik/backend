package com.hrksolutions.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.hrksolutions.authservice.entities.RefreshTokenEntity;
import com.hrksolutions.authservice.entities.UserEntity;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

	@Override
	Optional<RefreshTokenEntity> findById(Long id);

	Optional<RefreshTokenEntity> findByToken(String token);

//	void findByUserId(long userId);

	Optional<RefreshTokenEntity> findByUserEntity(UserEntity userEntity);

	Boolean existsByUserEntity(UserEntity userEntity);

	@Transactional
	Long deleteByUserEntity(UserEntity userEntity);
}
