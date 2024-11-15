package com.raontec.carbookingapi.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommDAO {
    List<Map<String, String>> selectCommonCode(String clsfCD);
}
