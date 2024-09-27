package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.CarDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/car")
public class CarController {

    private final CarDAO carDAO;

    @GetMapping(value="/selectCarList", produces = {"application/json"})
    public List<Map<String, String>> selectCarList() {
        return carDAO.selectCarList();
    }
}
