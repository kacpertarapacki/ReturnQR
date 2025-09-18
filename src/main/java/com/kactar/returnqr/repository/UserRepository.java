package com.kactar.returnqr.repository;

import com.kactar.returnqr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
