package org.hubson404.javadevelopertest.controllers;

import org.hubson404.javadevelopertest.model.report.ReportDto;
import org.hubson404.javadevelopertest.model.report.ReportWrapper;
import org.hubson404.javadevelopertest.model.starwarsapi.QueryCriteria;
import org.hubson404.javadevelopertest.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PutMapping("/reports/{report_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveReportToDatabase(@PathVariable(name = "report_id") Long reportId, @Valid @RequestBody QueryCriteria qc) {
        reportService.saveReport(reportId, qc);
    }

    @GetMapping("/reports")
    public ReportWrapper findAllReports() {
        return reportService.findAllReports();
    }

    @GetMapping("/reports/{report_id}")
    public ReportDto findReportById(@PathVariable(name = "report_id") Long reportId) {
        return reportService.findReportById(reportId);
    }

    @DeleteMapping("/reports/{report_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReportFromDatabase(@PathVariable(name = "report_id") Long reportId) {
        reportService.deleteReportById(reportId);
    }

    @DeleteMapping("/reports")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllReportsFromDatabase() {
        reportService.deleteAllReports();
    }

}

