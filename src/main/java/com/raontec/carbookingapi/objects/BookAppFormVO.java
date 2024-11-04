package com.raontec.carbookingapi.objects;

import jdk.jfr.Description;
import lombok.Data;
import lombok.NonNull;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("BookAppFormVO")
public class BookAppFormVO implements Serializable {
    @NonNull
    @Description(value = "시작일")
    private String startDate;

    @NonNull
    @Description(value = "시작시간")
    private String startTimeCd;

    @NonNull
    @Description(value = "종료일")
    private String endDate;

    @NonNull
    @Description(value = "종료시간")
    private String endTimeCd;

    @NonNull
    @Description(value = "차량 번호")
    private String carNumber;

    @NonNull
    @Description(value = "신청자")
    private String submitter;

    @NonNull
    @Description(value = "운전자")
    private String driver;

    @Description(value = "동승자")
    private String passengers;

    @NonNull
    @Description(value = "목적지")
    private String destination;

    @Description(value = "비고")
    private String rmrk;

    @Description(value = "사용목적")
    private String usePropose;

}
