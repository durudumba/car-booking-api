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

    List<Map<String, String>> getDrivingHistory(String userId);
    List<Map<String, String>> getDrivingSchedule(String userId);

}
