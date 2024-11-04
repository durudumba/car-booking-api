package com.raontec.carbookingapi.objects;

import lombok.Data;
import lombok.NonNull;

@Data
public class SignUpVO {
    @NonNull
    private String userId;
    @NonNull
    private String userName;
    @NonNull
    private String userPw;

    private String encodedUserPw;

    private String userRank;

}
