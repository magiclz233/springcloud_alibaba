package com.magic.system.repository;

import com.magic.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author magic_lz
 * @version 1.0
 * @classname RoleRepository
 * @date 2020/9/3 : 9:13
 */

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String roleName);
}
