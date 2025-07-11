package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.BookDAO;
import com.raontec.carbookingapi.data.CarDAO;
import com.raontec.carbookingapi.objects.BookAppFormVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookDAO bookDAO;
    private final CarDAO carDAO;

    @Description(value = "차량 사용 신청")
    @Transactional
    @PutMapping(value="", produces = {"application/json"})
    public ResponseEntity<?> carBooking(@Valid BookAppFormVO bookAppForm) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("stdt", bookAppForm.getStartDate());
            param.put("eddt", bookAppForm.getEndDate());
            if(carDAO.selectCarList(param).stream().filter(carInfo -> carInfo.get("CAR_NUM").equals(bookAppForm.getCarNumber())).count()==0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("일시적인 오류로 해당 차량 사용신청이 불가합니다!");
            }

            bookDAO.insertCarBook(bookAppForm);         // 차량 예약 스케줄 추가
        } catch (RuntimeException e) {
            log.error("carBooking RumtimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("차량 사용 신청 에러!");
        }
        return ResponseEntity.ok().build();
    }

    @Description(value = "운행 스케줄 조회")
    @GetMapping(value="/getDrivingSchedule", produces = {"application/json"})
    public ResponseEntity<?> getDrivingSchedule(@RequestParam(required = false) String userId,
            @RequestParam(required = false) String keyword) {
        List<Map<String, String>> resultList = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("keyword", keyword);
        try {
            resultList = bookDAO.getDrivingSchedule(param);

        } catch(RuntimeException e) {
            log.error("getDrivingSchedule RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("운행 스케줄 조회 에러!");
        }
        return ResponseEntity.ok(resultList);
    }

    @Description(value = "운행 기록 조회")
    @GetMapping(value = "/getDrivingHistory", produces = {"application/json"})
    public ResponseEntity<?> getDrivingHistory(@RequestParam(required = false) String userId,
           @RequestParam(required = false) String keyword) {
        List<Map<String, String>> resultList = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId);
        param.put("keyword", keyword);
        try {
            resultList = bookDAO.getDrivingHistory(param);
        } catch(RuntimeException e) {
            log.error("getDrivingHistory RumtimeException");
        }
        return ResponseEntity.ok(resultList);
    }

    @Description(value = "운행 후 주차위치 등록")
    @Transactional
    @PostMapping(value = "/postDrivingRecord", produces = {"application/json"})
    public ResponseEntity<?> postDrivingRecord(@RequestParam Map<String, String> param) {
        try {
            if(param.get("DRIV_YN").equals("Y") && !bookDAO.getPrevSchedule(param).isEmpty()) {
                return ResponseEntity.status(HttpStatus.LOCKED)
                        .body("해당 차량에 완료되지 않은 이전 일정이 있는 경우 등록할 수 없습니다.");
            }

            bookDAO.insertCarBookHistory(param);
            bookDAO.updateParkingLocation(param);
            bookDAO.deleteCarSchedule(param);
        } catch(RuntimeException e) {
            log.error("postDrivingRecord RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("운행기록 작성에러!");
        }
        return ResponseEntity.ok().build();
    }

    @Description(value = "주차위치 미등록 예약정보 조회")
    @Transactional
    @GetMapping(value = "/getUnrecordedBooking", produces = {"application/json"})
    public ResponseEntity<?> getUnrecordedBooking(@RequestParam(required = false) String carNumber,
          @RequestParam(required = false) String keyword) {
        List<Map<String, String>> resultList = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("carNumber", carNumber);
        param.put("keyword", keyword);

        try {
            resultList = bookDAO.getUnrecordedBooking(param);
        } catch(RuntimeException e) {
            log.error("getUnrecordedBooking RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("미등록 예약정보 조회 에러!");
        }
        return ResponseEntity.ok(resultList);
    }

    @Description(value = "운행 정보 수정")
    @PostMapping(value = "/updateDrivingInfo", produces = {"application/json"})
    public ResponseEntity<?> updateDrivingInfo(@RequestParam Map<String, String> param) {
        try {
            bookDAO.updateDrivingInfo(param);
        } catch (RuntimeException e) {
            log.error("updateDrivingInfo RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("운행 정보 수정 에러!");
        }
        return ResponseEntity.ok().build();
    }

    @Description(value = "주차위치 자동적용")
    public void setCurrentParkingLocation() {
    }
}
