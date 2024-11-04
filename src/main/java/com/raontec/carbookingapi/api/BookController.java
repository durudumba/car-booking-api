package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.BookDAO;
import com.raontec.carbookingapi.objects.BookAppFormVO;
import jakarta.validation.Valid;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/book")
public class BookController {

    private final BookDAO bookDAO;

    @Description(value = "차량 사용 신청")
    @PutMapping(value="", produces = {"application/json"})
    public int carBooking(@Valid BookAppFormVO bookAppForm) {
        int result = 0;
        // 차량 예약 스케줄 추가
        result *= bookDAO.insertCarBook(bookAppForm);

        // 차량 예약 기록 추가
        result *= bookDAO.insertCarBookHistory(bookAppForm);

        return result;
    }
}
