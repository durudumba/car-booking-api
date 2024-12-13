package com.raontec.carbookingapi.data;

import com.raontec.carbookingapi.objects.BookAppFormVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookDAO {

    int insertCarBook(BookAppFormVO bookAppForm);
    int insertCarBookHistory(Map map);
    int updateParkingLocation(Map map);
    int deleteCarSchedule(Map map);
    int updateDrivingInfo(Map map);

    List<Map<String, String>> getDrivingHistory(Map map);
    List<Map<String, String>> getDrivingSchedule(Map map);
    List<Map<String, String>> getUnrecordedBooking(Map map);
}
