package com.raontec.carbookingapi.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarDAO {

    List<Map<String, String>> selectCarList();
}
