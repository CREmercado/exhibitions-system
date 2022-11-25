package com.epam.exhibitions.repository;

import com.epam.exhibitions.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("FROM User a WHERE a.nickname = :nickname")
    Optional<User> findUserByNickname(String nickname);

}
