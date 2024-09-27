package com.raontec.carbookingapi.data;

import com.raontec.carbookingapi.vo.BookAppFormVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookDAO {

    int insertCarBook(BookAppFormVO bookAppForm);
    int insertCarBookHistory(BookAppFormVO bookAppForm);

}
