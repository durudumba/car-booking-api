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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/car/")
public class CarController {

    private final CarDAO carDAO;

    @Description(value = "예약가능 차량목록")
    @GetMapping(value = "/selectCarList", produces = {"application/json"})
    public ResponseEntity<?> selectCarList(@RequestParam Map<String, String> param) {
        List<Map<String, String>> resultList = new ArrayList<>();

        try {
            resultList = carDAO.selectCarList(param);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("예약가능 차량목록 조회 에러!");
        }
        return ResponseEntity.ok(resultList);
    }

    @Description(value = "차량관리 차량목록")
    @GetMapping(value = "/getCarsInfo", produces = {"application/json"})
    public ResponseEntity<?> getCarsInfo() {
        List<Map<String, String>> resultList = new ArrayList<>();

        try{
            resultList = carDAO.getCarsInfo();
        } catch (RuntimeException e) {
            log.error("getCarsInfo RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("차량목록 조회 에러!");
        }
        return ResponseEntity.ok(resultList);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("차량 추가 에러!");
        }
        return ResponseEntity.ok().build();
    }

    @Description(value = "차량관리 차량정보수정")
    @Transactional
    @PostMapping(value = "/updateCarInfo", produces = {"application/json"})
    public ResponseEntity<?> updateCarInfo(@RequestParam Map<String, String> param) {
        String modCarNumber = param.get("modCarNumber");

        try {
            carDAO.updateCarInfo(param);
            if(modCarNumber != null && modCarNumber.equals("true")) {
                carDAO.updateCarNumber(param);
            }
        } catch(RuntimeException e) {
            log.error("updateCarInfo RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("차량 정보 수정 에러!");
        }

        return ResponseEntity.ok().build();
    }

    @Description(value = "차량관리 차량정보삭제")
    @PostMapping(value = "/deleteCarInfo", produces = {"application/json"})
    public ResponseEntity<?> deleteCarInfo(@RequestParam Map<String, String> param) {
        if(!carDAO.getCarBookSchedule(param).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("예약된 일정이 있어 차량을 삭제할 수 없습니다");
        }
        try {
            carDAO.deleteCarInfo(param);
        } catch(RuntimeException e) {
            log.error("deleteCarInfo RuntimeException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("차량 정보 삭제 에러!");
        }

        return ResponseEntity.ok().build();
    }
}
