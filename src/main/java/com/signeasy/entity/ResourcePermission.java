package com.signeasy.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Table(name = "RESOURCE_PERMISSION")
@Getter
@Setter
@Entity
public class ResourcePermission {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "resource_id", referencedColumnName = "id")
	private Resource resource;
	
	@Column(name="permission", nullable = false, unique=true)
	private String permission;
	
	@ManyToMany(mappedBy = "resourcePermissions")
    private Set<Role> roles = new HashSet<>();
	
}
