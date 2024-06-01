package com.ing.stockexchange.repository;

import com.ing.stockexchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer>
{
    @Query("""
        SELECT u FROM User u WHERE u.username = :username
    """)
    Optional<User> findUserByUserName(String username);
}
