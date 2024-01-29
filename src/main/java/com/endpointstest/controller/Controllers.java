package com.endpointstest.controller;

import com.endpointstest.dto.Request;
import com.endpointstest.dto.ResponseTest;
import com.endpointstest.dto.ResponseToCompare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class Controllers {

    Logger logger = LoggerFactory.getLogger(Controllers.class);
    private static String TAX_RATE = "1.5";

    @PostMapping("/test")
    public ResponseEntity<ResponseTest> getTest(@RequestBody Request req) {

        String rate =
                BigDecimal.valueOf(req.getPercentage()).multiply(new BigDecimal(TAX_RATE)).toString();
        var response = ResponseTest.builder()
                .id(1)
                .name("test")
                .rate(rate)
                .build();

        return ResponseEntity.ok(response);

    }

    @PostMapping("/test-compare")
    public ResponseEntity<ResponseToCompare> getTestToCompare(@RequestBody Request req) {
        String rate =
                BigDecimal.valueOf(req.getPercentage()).multiply(new BigDecimal(TAX_RATE)).toString();
        var response = ResponseToCompare.builder()
                .id(2)
                .rate(rate)
                .status("sarasa")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ResponseTest>> printBatch(@RequestBody List<Request> reqs) {
        logger.debug("batch start: " + reqs.size());
        List<ResponseTest> result = reqs.parallelStream()
                .map(r -> {
                    String rate = BigDecimal.valueOf(r.getPercentage()).multiply(new BigDecimal(TAX_RATE)).toString();
                    return ResponseTest.builder()
                            .id(1)
                            .name("test " + r.getName())
                            .rate(rate)
                            .build();
                })
                .toList();
        logger.debug("new Bach: " + result.size());
        return ResponseEntity.ok(result);
    }

    private static String formatDecimalString(String input) {
        BigDecimal decimalValue = new BigDecimal(input);
        return decimalValue.stripTrailingZeros().toPlainString();
    }
}
