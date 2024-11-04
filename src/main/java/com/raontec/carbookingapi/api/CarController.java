package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.CarDAO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
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

    @PostConstruct
    @Scheduled(cron = "0 59 23 * * ?")
    public ResponseEntity<?> deleteExpiredSchedule() {
        return ResponseEntity.ok(carDAO.deleteExpiredSchedule());
    }

    @GetMapping(value="selectCarList", produces = {"application/json"})
    public ResponseEntity<?> selectCarList(@RequestParam Map<String, String> param) {
        try {
            return ResponseEntity.ok(carDAO.selectCarList(param));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
