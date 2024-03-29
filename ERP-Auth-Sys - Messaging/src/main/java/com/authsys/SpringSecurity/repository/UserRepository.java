package com.authsys.SpringSecurity.repository;

import com.authsys.SpringSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author shuang.kou
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findById(Long userId);
    Optional<User> findByUserName(String username);

    boolean existsByUserName(String username);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByUserName(String userName);
}
