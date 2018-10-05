package com.firstgenix.model.security;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "AUTHORITY")
public class Authority implements Serializable {

    @Id
    @Column(name = "ID")
   // @GeneratedValue(strategy = GenerationType.AUTO)
  //@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "authority_seq")
 //   @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
    private String id=UUID.randomUUID().toString().toUpperCase();

    @Column(name = "NAME", length = 50)
    @NotNull
   // @Enumerated(EnumType.STRING)
    private String name;

    @ManyToMany(mappedBy = "authorities",fetch = FetchType.LAZY)
    private List<User> users;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Permission_AUTHORITY",
            joinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID")})
    private List<Permission> permission;
    

    public List<Permission> getPermission() {
		return permission;
	}

	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}

	public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    private List<Permission> tostring() {
		return this.permission;
	}
}