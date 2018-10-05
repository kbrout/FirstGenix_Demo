package com.firstgenix.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.firstgenix.model.security.Permission;
import com.firstgenix.model.security.User;

public interface PermisssionRepository extends JpaRepository<Permission,Long>{
	 Permission findByName(String name);
}
