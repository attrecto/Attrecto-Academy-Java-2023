package com.attrecto.academy.java.courseapp.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attrecto.academy.java.courseapp.model.User;
import com.attrecto.academy.java.courseapp.model.dto.UserDto;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByName(String name);

	public List<User> findByNameContainingIgnoreCaseOrderByIdAscName(String name);
}
