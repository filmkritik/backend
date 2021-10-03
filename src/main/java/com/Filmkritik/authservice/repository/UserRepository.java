package com.Filmkritik.authservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.Filmkritik.authservice.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUsername(String username);
}
