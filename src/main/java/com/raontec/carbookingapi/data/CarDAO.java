package com.raontec.carbookingapi.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarDAO {

    List<Map<String, String>> selectCarList(Map<String, String> map);
    List<Map<String, String>> getCarsInfo();
    Map<String, String> getCarInfo(String carNumber);
    List<Map<String, String>> getCarBookSchedule(Map<String, String> map);

    int insertCarInfo(Map<String, String> map);
    int insertCarStatus(Map<String, String> map);
    int insertParkingLoc(Map<String, String> map);

    int updateCarInfo(Map<String, String> map);
    int updateCarNumber(Map<String, String> map);
    int deleteCarInfo(Map<String, String> map);

    int deleteExpiredSchedule();
}
