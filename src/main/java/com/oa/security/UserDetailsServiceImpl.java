package com.oa.security;

import com.oa.common.exception.BusinessException;
import com.oa.system.entity.User;
import com.oa.system.mapper.MenuMapper;
import com.oa.system.mapper.UserMapper;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    private final MenuMapper menuMapper;

    public UserDetailsServiceImpl(UserMapper userMapper, MenuMapper menuMapper) {
        this.userMapper = userMapper;
        this.menuMapper = menuMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new DisabledException("User is disabled");
        }
        List<SimpleGrantedAuthority> authorities = menuMapper.selectPermsByUserId(user.getUserId())
                .stream()
                .filter(p -> p != null && !p.isBlank())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
