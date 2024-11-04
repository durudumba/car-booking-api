package com.raontec.carbookingapi.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserVO {
    private String userId;

    private String userPw;

    private String userName;

    private EnumCollection authority = EnumCollection.ROLE_ADMIN;
}
