package com.signeasy.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name = "ROLE")
@Getter
@Setter
@Entity
public class Role {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private int id;
	

	@Column(name="name")
	private String name;
	
	@ManyToMany(cascade = { CascadeType.ALL }, fetch =  FetchType.EAGER)
    @JoinTable(
        name = "ROLE_RESOURCE_PERMISSION", 
        joinColumns = { @JoinColumn(name = "role_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "resource_permission_id") }
    )
	Set<ResourcePermission> resourcePermissions = new HashSet<ResourcePermission>();
	
	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
	private Set<User> users = new HashSet<>();
}
