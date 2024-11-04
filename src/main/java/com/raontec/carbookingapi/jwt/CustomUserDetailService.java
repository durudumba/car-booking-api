package com.raontec.carbookingapi.jwt;

import com.raontec.carbookingapi.data.UserDAO;
import com.raontec.carbookingapi.objects.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserVO> userInfo = userDAO.getUserInfo(username);

        return userInfo.map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username+ "-> 등록되지 않은 사용자입니다."));
    }

    private UserDetails createUserDetails(UserVO userVo) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userVo.getAuthority().toString());

        return new User(
                String.valueOf(userVo.getUserId()),
                userVo.getUserPw(),
                Collections.singleton(grantedAuthority)
        );
    }
}
