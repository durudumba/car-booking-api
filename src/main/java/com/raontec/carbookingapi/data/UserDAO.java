package com.raontec.carbookingapi.data;

import com.raontec.carbookingapi.objects.SignUpVO;
import com.raontec.carbookingapi.objects.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface UserDAO {

    int insertUser(Map map);
    int updateUserInfo(Map map);
    int deleteUserInfo(Map map);
    int deleteUserSchedule(Map map);

    String isDeniedUser(String userId);
    Map<String, String> getPageAccessAuth(Map map);

    Optional<UserVO> getUserInfo(String userId);
    Map<String, String> selectUserInfo(String userId);
    List<Map<String, String>> getAccessableMenuList(String userId);
    List<Map<String, String>> selectUserList();
}
