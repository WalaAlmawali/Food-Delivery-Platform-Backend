package com.example.Food.Delivery.Platform.Backend.Controllers;

import com.example.Food.Delivery.Platform.Backend.Services.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ReportingController {

    private final ReportService reportService;

    @GetMapping("/revenue/restaurant/{restaurantId}")
    public BigDecimal getRestaurantRevenue(
            @PathVariable Integer restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return reportService.getRestaurantRevenue(restaurantId, date);
    }

    @GetMapping("/orders/count/restaurant/{restaurantId}")
    public long getRestaurantOrderCount(@PathVariable Integer restaurantId) {
        return reportService.getRestaurantOrderCount(restaurantId);
    }
}
