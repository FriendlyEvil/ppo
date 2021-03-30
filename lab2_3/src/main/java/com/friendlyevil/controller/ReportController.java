package com.friendlyevil.controller;

import com.friendlyevil.dto.AvgReport;
import com.friendlyevil.dto.DayReports;
import com.friendlyevil.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author friendlyevil
 */
@RestController
@RequestMapping("report")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/avg")
    public AvgReport getAvgReport() {
        return reportService.getAvgReport();
    }

    @GetMapping("/days")
    public DayReports getDayReport() {
        return reportService.getDayReports();
    }

    public void clear() {
        reportService.clear();
    }

}
