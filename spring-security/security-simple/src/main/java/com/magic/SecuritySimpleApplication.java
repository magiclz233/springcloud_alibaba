package com.magic;

import com.magic.system.entity.Role;
import com.magic.system.entity.User;
import com.magic.system.entity.UserRole;
import com.magic.system.enums.RoleType;
import com.magic.system.repository.RoleRepository;
import com.magic.system.repository.UserRepository;
import com.magic.system.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author magic
 */
@SpringBootApplication
public class SecuritySimpleApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SecuritySimpleApplication.class, args);
    }


    /**
     * 系统启动  初始化数据
     * @param args 字符串数组
     */
    @Override
    public void run(String... args) throws Exception {
        for (String arg : args) {
            System.out.println("arg : " + arg);
        }
        // 初始化角色信息
        for (RoleType roleType : RoleType.values()) {
            roleRepository.save(new Role(roleType.getName(), roleType.getDescription()));
        }

        // 初始化一个admin用户
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        User user = User.builder().enabled(true).fullName("admin").userName("magic").password(bc.encode("123456")).build();
        userRepository.save(user);
        Role role = roleRepository.findByName(RoleType.ADMIN.getName()).get();
        userRoleRepository.save(new UserRole(user, role));
    }
}
