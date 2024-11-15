package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.UserDAO;
import com.raontec.carbookingapi.jwt.LoginRequestDto;
import com.raontec.carbookingapi.jwt.TokenDto;
import com.raontec.carbookingapi.jwt.TokenProvider;
import com.raontec.carbookingapi.objects.SignUpVO;
import com.raontec.carbookingapi.objects.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {
    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @PostMapping(value="/login")
    public ResponseEntity<?> login (LoginRequestDto loginDto, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            TokenDto tokenDto = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PostMapping(value="/sign-up", produces = {"application/json"})
    public ResponseEntity<?> insertUser(UserVO vo) {
        int result = 0;

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", vo.getUserId());
        map.put("userPw", passwordEncoder.encode(vo.getUserPw()));
        map.put("userName", vo.getUserName());

        try {
            result = userDAO.insertUser(map);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/selectUserInfo", produces = {"application/json"})
    public ResponseEntity<?> selectUserInfo(@RequestParam(value = "user_id") String userId) {
        Map<String, String> resultMap = userDAO.selectUserInfo(userId);

        return ResponseEntity.ok(resultMap);
    }


    @GetMapping(value = "/getAccessableMenuList", produces = {"application/json"})
    public ResponseEntity<?> getAccessableMenuList(@RequestParam(value = "user_id") String userId) {
        List<Map<String, String>> resultList = userDAO.getAccessableMenuList(userId);

        return ResponseEntity.ok(resultList);
    }
}
