package com.signeasy.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "RESOURCE")
@Getter
@Setter
@Entity
public class Resource implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="name", nullable = false, unique=true)
	private String name;
	
	@OneToMany(mappedBy = "resource", fetch = FetchType.EAGER)
	private Set<ResourcePermission> resourcePermissions = new HashSet<>();
	
	
}