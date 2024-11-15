package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.BookDAO;
import com.raontec.carbookingapi.objects.BookAppFormVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/book")
public class BookController {

    private final BookDAO bookDAO;

    @Description(value = "차량 사용 신청")
    @Transactional
    @PutMapping(value="", produces = {"application/json"})
    public ResponseEntity<?> carBooking(@Valid BookAppFormVO bookAppForm) {

        try {
            bookDAO.insertCarBook(bookAppForm);         // 차량 예약 스케줄 추가
            bookDAO.insertCarBookHistory(bookAppForm);  // 차량 예약 기록 추가
        } catch (RuntimeException e) {
            log.error("carBooking RumtimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("차량 사용 신청 에러!");
        }
        return ResponseEntity.ok().build();
    }

    @Description(value = "사용자별 운행 스케줄 조회")
    @GetMapping(value="/getDrivingSchedule", produces = {"application/json"})
    public ResponseEntity<?> getDrivingSchedule(@RequestParam String userId) {
        return ResponseEntity.ok(bookDAO.getDrivingSchedule(userId));
    }

    @Description(value = "사용자별 운행 기록 조회")
    @GetMapping(value = "/getDrivingHistory", produces = {"application/json"})
    public ResponseEntity<?> getDrivingHistory(@RequestParam String userId) {
        return ResponseEntity.ok(bookDAO.getDrivingHistory(userId));
    }

    @Description(value = "주차위치 자동적용")
    public void setCurrentParkingLocation() {
    }
}
