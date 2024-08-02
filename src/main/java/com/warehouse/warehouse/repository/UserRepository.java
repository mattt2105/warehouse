package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
