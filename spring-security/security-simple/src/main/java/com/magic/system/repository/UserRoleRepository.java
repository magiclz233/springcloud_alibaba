package com.magic.system.repository;

import com.magic.system.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author magic_lz
 * @version 1.0
 * @classname UserRoleRepository
 * @date 2020/9/3 : 9:19
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
