package com.raontec.carbookingapi.objects;

import lombok.Data;

@Data
public class UserVO {
    private String userId;

    private String userPw = "1234";

    private int userRank = 1;

    private EnumCollection authority = EnumCollection.ROLE_ADMIN;
}
