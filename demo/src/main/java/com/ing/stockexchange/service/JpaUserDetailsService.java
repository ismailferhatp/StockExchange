package com.ing.stockexchange.service;

import com.ing.stockexchange.repository.UserRepository;
import com.ing.stockexchange.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        var u = userRepository.findUserByUserName(username);
        return u.map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found" + username));
    }
}
