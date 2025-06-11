package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.UserDAO;
import com.raontec.carbookingapi.jwt.LoginRequestDto;
import com.raontec.carbookingapi.jwt.TokenDto;
import com.raontec.carbookingapi.jwt.TokenProvider;
import com.raontec.carbookingapi.objects.UserVO;
import com.raontec.carbookingapi.objects.pwChangeVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
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

    @Description(value = "로그인")
    @PostMapping(value="/login")
    public ResponseEntity<?> login (LoginRequestDto loginDto, HttpServletRequest request) {
        try {
            if(userDAO.isDeniedUser(loginDto.getUserId()).equals("Y")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("접근 제한된 계정입니다");
            }
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("아이디와 비밀번호를 확인하세요");
        }


        try {
            UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            TokenDto tokenDto = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("아이디와 비밀번호를 확인하세요");
        }
    }

    @Description(value = "사용자 등록")
    @Transactional
    @PostMapping(value="/insertUser", produces = {"application/json"})
    public ResponseEntity<?> insertUser(UserVO vo, HttpServletRequest request) {
        if(userDAO.getUserInfo(vo.getUserId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("해당 ID는 사용할 수 없습니다");
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("userId", vo.getUserId());
        map.put("userPw", passwordEncoder.encode(vo.getUserPw()));
        map.put("userRank", String.valueOf(vo.getUserRank()));

        try {
            userDAO.insertUser(map);
        } catch (RuntimeException e) {
            log.error("insertUser RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자등록 실패");
        }

        return ResponseEntity.ok().build();
    }

    @Description(value = "사용자 정보 조회")
    @PostMapping(value = "/selectUserInfo", produces = {"application/json"})
    public ResponseEntity<?> selectUserInfo(@RequestParam(value = "user_id") String userId) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap = userDAO.selectUserInfo(userId);
        } catch (RuntimeException e) {
            log.error("selectUserInfo RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 정보조회 에러!");
        }
        return ResponseEntity.ok(resultMap);
    }


    @Description(value = "사용자별 접근가능 메뉴목록")
    @GetMapping(value = "/getAccessableMenuList", produces = {"application/json"})
    public ResponseEntity<?> getAccessableMenuList(@RequestParam(value = "user_id") String userId) {
        List<Map<String, String>> resultList = new ArrayList<>();
        try {
            resultList = userDAO.getAccessableMenuList(userId);
        } catch (RuntimeException e) {
            log.error("getAccessableMenuList RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("접근가능 메뉴목록 조회 에러!");
        }

        return ResponseEntity.ok(resultList);
    }

    @Description(value = "사용자 목록 조회")
    @GetMapping(value = "/selectUserList", produces = {"application/json"})
    public ResponseEntity<?> selectUserList(){
        List<Map<String, String>> resultList = new ArrayList<>();
        try {
            resultList = userDAO.selectUserList();
        } catch (RuntimeException e) {
            log.error("selectUserList RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 목록 조회 에러!");
        }
        return ResponseEntity.ok(resultList);
    }

    @Description(value = "사용자 접근권한 조회")
    @PostMapping(value = "/getPageAccessAuth", produces = {"application/json"})
    public ResponseEntity<?> getPageAccessAuth(@RequestParam Map<String, String> param) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            resultMap = userDAO.getPageAccessAuth(param);

        } catch (RuntimeException e) {
            log.error("getPageAccessAuth RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 접근권한 조회 에러!");
        }
        return ResponseEntity.ok(resultMap);
    }

    @Description(value = "사용자 정보 수정")
    @Transactional
    @PostMapping(value = "/updateUserInfo", produces = {"application/json"})
    public ResponseEntity<?> updateUserInfo(@RequestParam Map<String, String> param) {
        try {
            if(param.get("denyUseYN").equals("Y")) {
                userDAO.deleteUserSchedule(param);
            }
            userDAO.updateUserInfo(param);
        } catch (RuntimeException e) {
            log.error("updateUserInfo RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 정보 수정 에러!");
        }
        return ResponseEntity.ok().build();
    }

    @Description(value = "사용자 정보 삭제")
    @Transactional
    @DeleteMapping(value = "/deleteUserInfo", produces = {"application/json"})
    public ResponseEntity<?> deleteUserInfo(@RequestParam Map<String, String> param) {
        try {
            userDAO.deleteUserSchedule(param);
            userDAO.deleteUserInfo(param);
        } catch (RuntimeException e) {
            log.error("deleteUserInfo RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 정보 삭제 에러!");
        }
        return ResponseEntity.ok().build();
    }

    @Description(value = "사용자 비밀번호 변경")
    @Transactional
    @PostMapping(value = "/userPwChange", produces = {"application/json"})
    public ResponseEntity<?> userPwChange(pwChangeVO vo) {
        try {
            Map<String, String> userInfo = userDAO.selectUserInfo(vo.getUserId());

            if(!passwordEncoder.matches(vo.getCurPw(), userInfo.get("USER_PW"))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("현재 비밀번호와 다릅니다");
            }

            vo.setEncodedNewPw(passwordEncoder.encode(vo.getNewPw()));
            userDAO.updateNewPw(vo);
        } catch(RuntimeException e) {
            log.error("userPwChange RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 비밀번호 변경 에러!");
        }
        return ResponseEntity.ok().build();
    }
}
