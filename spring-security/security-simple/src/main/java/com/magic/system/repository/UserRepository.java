package com.magic.system.repository;

import com.magic.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author magic_lz
 * @version 1.0
 * @classname UserRepository
 * @date 2020/9/3 : 9:16
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String username);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByUserName(String username);

    boolean existsByUserName(String username);
}
