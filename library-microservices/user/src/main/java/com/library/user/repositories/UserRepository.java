package com.library.user.repositories;

import com.library.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u where u.userEmail = :user_email")
    User findUserByEmail(@Param(value = "user_email") String email);
}
