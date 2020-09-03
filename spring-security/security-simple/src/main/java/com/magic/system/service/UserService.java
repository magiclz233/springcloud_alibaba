package com.magic.system.service;

import com.google.common.collect.ImmutableMap;
import com.magic.system.entity.Role;
import com.magic.system.entity.User;
import com.magic.system.entity.UserRole;
import com.magic.system.enums.RoleType;
import com.magic.system.exception.RoleNotFoundException;
import com.magic.system.exception.UserNameAlreadyExistException;
import com.magic.system.exception.UserNameNotFoundException;
import com.magic.system.repository.RoleRepository;
import com.magic.system.repository.UserRepository;
import com.magic.system.repository.UserRoleRepository;
import com.magic.system.web.representation.UserRepresentation;
import com.magic.system.web.request.UserRegisterRequest;
import com.magic.system.web.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author magic_lz
 * @version 1.0
 * @classname UserService
 * @date 2020/9/3 : 14:35
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    public static final String USERNAME = "username:";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public User find(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UserNameNotFoundException(ImmutableMap.of(USERNAME, username)));
    }

    private void ensureUserNameNotExist(String userName) {
        boolean exist = userRepository.findByUserName(userName).isPresent();
        if (exist) {
            throw new UserNameAlreadyExistException(ImmutableMap.of(USERNAME, userName));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(UserRegisterRequest userRegisterRequest) {
        ensureUserNameNotExist(userRegisterRequest.getUserName());
        User user = userRegisterRequest.toUser();
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
        userRepository.save(user);
        // 给用户绑定角色
        Role studentRole = roleRepository.findByName(RoleType.USER.getName()).orElseThrow(() -> new RoleNotFoundException(ImmutableMap.of("roleName", RoleType.USER.getName())));
        Role managerRole = roleRepository.findByName(RoleType.MANAGER.getName()).orElseThrow(() -> new RoleNotFoundException(ImmutableMap.of("roleName", RoleType.MANAGER.getName())));
        userRoleRepository.save(new UserRole(user, studentRole));
        userRoleRepository.save(new UserRole(user, managerRole));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateRequest userUpdateRequest){
        User user = find(userUpdateRequest.getUserName());
        if(Objects.nonNull(userUpdateRequest.getFullName())){
            user.setFullName(userUpdateRequest.getFullName());
        }
        if (Objects.nonNull(userUpdateRequest.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userUpdateRequest.getPassword()));
        }
        if (Objects.nonNull(userUpdateRequest.getEnabled())) {
            user.setEnabled(userUpdateRequest.getEnabled());
        }
        userRepository.save(user);
    }

    public void delete(String userName) {
        if (!userRepository.existsByUserName(userName)) {
            throw new UserNameNotFoundException(ImmutableMap.of(USERNAME, userName));
        }
        userRepository.deleteByUserName(userName);
    }

    public Page<UserRepresentation> getAll(int pageNum,int pageSize){
        return userRepository.findAll(PageRequest.of(pageNum,pageSize)).map(User::toUserRepresentation);
    }

}
