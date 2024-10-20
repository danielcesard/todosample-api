package com.daniel.todosample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.todosample.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
