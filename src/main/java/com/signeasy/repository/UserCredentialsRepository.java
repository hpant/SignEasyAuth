package com.signeasy.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.signeasy.entity.UserCredentials;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer>{

	public UserCredentials getByClientKeyAndSecret(String clientKey, String secret);
	
	@Modifying
	@Transactional
	@Query(value = "update USER_CREDENTIALS set user_token = :userToken, token_level = :tokenLevel where id = :id", nativeQuery =  true)
	void updateToken(@Param(value="id") int id, @Param(value="userToken")String userToken, @Param(value="tokenLevel")String tokenLevel);
}
