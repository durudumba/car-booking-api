package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.CarDAO;
import com.raontec.carbookingapi.vo.BookAppFormVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value="selectCarList", produces = {"application/json"})
    public List<Map<String, String>> selectCarList(@RequestParam Map<String, String> param) {
        return carDAO.selectCarList(param);
    }
}
