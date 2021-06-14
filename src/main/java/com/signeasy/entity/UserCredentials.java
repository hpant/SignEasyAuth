package com.signeasy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table(name = "USER_CREDENTIALS")
@Entity
public class UserCredentials {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="client_key")
	private String clientKey;
	
	@Column(name="secret")
	private String secret;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
//	@OneToOne(mappedBy = "userCredentials")
//    private UserToken userToken;
	
	@Column(name="user_token")
	private String userToken;
	
	@Column(name="token_level")
	private String tokenLevel;
	
//	@Column(name="created_at")
//	private Date createdAt;
//	
//	@Column(name="updated_at")
//	private Date updatedAt;
}
