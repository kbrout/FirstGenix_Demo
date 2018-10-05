package com.firstgenix.model.security;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PERMISSION")
public class Permission {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
    
    @Column(name = "NAME", length = 50)
    @NotNull
   // @Enumerated(EnumType.STRING)
	private String name;
    
   /* @ManyToMany(mappedBy = "permission", fetch = FetchType.LAZY)
    private List<User> users;*/
   /* @OneToMany(mappedBy="permissionId")
    private Set<TRolePermission> rolePermission;*/
    
    @ManyToMany(mappedBy = "permission",fetch = FetchType.LAZY)
    private List<Authority> authorities;

	public List<Authority> getAuthorities() {
		return authorities;
	}


	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	/*public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}*/
    
    
    
}
