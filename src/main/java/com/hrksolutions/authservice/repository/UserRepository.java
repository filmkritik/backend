package com.hrksolutions.authservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.hrksolutions.authservice.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);
}
