package com.raontec.carbookingapi.objects;

import lombok.Data;
import lombok.NonNull;

@Data
public class pwChangeVO {
    @NonNull
    private String userId;
    @NonNull
    private String curPw;
    @NonNull
    private String newPw;

    private String encodedNewPw;
}
