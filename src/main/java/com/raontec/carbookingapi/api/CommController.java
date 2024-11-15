package com.raontec.carbookingapi.api;

import com.raontec.carbookingapi.data.CommDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/comm/")
public class CommController {

    private final CommDAO commDAO;

    @GetMapping(value="/getCommCodeList")
    public ResponseEntity<?> getCommCodeList() {
        Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
        resultMap.put("fuelTypeList", commDAO.selectCommonCode("FTC"));
        resultMap.put("carStatusList", commDAO.selectCommonCode("CST"));

        return ResponseEntity.ok(resultMap);
    }
}
