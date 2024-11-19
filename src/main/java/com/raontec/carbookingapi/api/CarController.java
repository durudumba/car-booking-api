package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.CarDAO;
import jakarta.annotation.PostConstruct;
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
@RestController
@RequestMapping("/api/car/")
public class CarController {

    private final CarDAO carDAO;

//    @Description(value="만료된 차량예약 스케줄 삭제")
//    @PostConstruct
//    @Scheduled(cron = "0 59 23 * * ?")
//    public ResponseEntity<?> deleteExpiredSchedule() {
//        return ResponseEntity.ok(carDAO.deleteExpiredSchedule());
//    }

    @Description(value = "예약가능 차량목록")
    @GetMapping(value = "/selectCarList", produces = {"application/json"})
    public ResponseEntity<?> selectCarList(@RequestParam Map<String, String> param) {
        return ResponseEntity.ok(carDAO.selectCarList(param));
    }

    @Description(value = "차량관리 차량목록")
    @GetMapping(value = "/getCarsInfo", produces = {"application/json"})
    public ResponseEntity<?> getCarsInfo() {
        return ResponseEntity.ok(carDAO.getCarsInfo());
    }

    @Description(value = "차량관리 차량정보추가")
    @Transactional
    @PostMapping(value = "/insertCarInfo", produces = {"application/json"})
    public ResponseEntity<?> insertCarInfo(@RequestParam Map<String, String> param) {
        try {
            carDAO.insertCarInfo(param);
            carDAO.insertCarStatus(param);
            carDAO.insertParkingLoc(param);
        } catch(RuntimeException e) {
            log.error("insertCarInfo RuntimeException");
        }
        return ResponseEntity.ok().build();
    }

    @Description(value = "차량관리 차량정보수정")
    @Transactional
    @PostMapping(value = "/updateCarInfo", produces = {"application/json"})
    public ResponseEntity<?> updateCarInfo(@RequestParam Map<String, String> param) {
        String modCarNumber = param.get("modCarNumber");

        carDAO.updateCarInfo(param);
        if(modCarNumber != null && modCarNumber.equals("true")) {
            carDAO.updateCarNumber(param);
        }

        return ResponseEntity.ok("");
    }

    @Description(value = "차량관리 차량정보삭제")
    @PostMapping(value = "/deleteCarInfo", produces = {"application/json"})
    public ResponseEntity<?> deleteCarInfo(@RequestParam Map<String, String> param) {
        if(!carDAO.getCarBookSchedule(param).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("삭제 에러! : 예약된 일정 존재");
        }
        return ResponseEntity.ok(carDAO.deleteCarInfo(param));
    }
}
