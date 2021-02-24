package org.hubson404.javadevelopertest.services;

import org.hubson404.javadevelopertest.domain.Report;
import org.hubson404.javadevelopertest.exceptions.ReportNotFoundExeption;
import org.hubson404.javadevelopertest.model.report.ReportDto;
import org.hubson404.javadevelopertest.model.report.ReportMapper;
import org.hubson404.javadevelopertest.model.report.ReportWrapper;
import org.hubson404.javadevelopertest.model.starwarsapi.QueryCriteria;
import org.hubson404.javadevelopertest.model.starwarsapi.StarWarsApiResponseData;
import org.hubson404.javadevelopertest.repositories.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceUnitTest {

    @Mock
    ReportRepository reportRepository;

    @Mock
    StarWarsApiService starWarsApiService;

    @Mock
    ReportMapper reportMapper;

    @InjectMocks
    ReportService reportService;

    @Test
    void findAllReports_callsReportRepository() {
        // given
        when(reportRepository.findAll()).thenReturn(List.of(new Report(), new Report()));
        when(reportMapper.toReportDto(any(Report.class))).thenReturn(new ReportDto());
        // when
        ReportWrapper result = reportService.findAllReports();
        // then
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    void findReportById_callsReportRepository() {
        // given
        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(new Report()));
        when(reportMapper.toReportDto(any(Report.class))).thenReturn(new ReportDto());
        // when
        reportService.findReportById(anyLong());
        // then
        verify(reportRepository, times(1)).findById(anyLong());
    }

    @Test
    void findReportById_ReportByGivenIdDoesNotExist_callsReportRepositoryAndThrowsException() {
        // given
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> reportService.findReportById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(ReportNotFoundExeption.class);
        verify(reportRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteReportById_callsReportRepository() {
        // given
        long reportId = 1L;
        Report report = Report.builder().reportId(reportId).build();
        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));
        // when
        reportService.deleteReportById(reportId);
        // then
        verify(reportRepository, times(1)).findById(anyLong());
        verify(reportRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteReportById_ReportByIdNotFound_ThrowsException() {
        // given
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Throwable result = catchThrowable(() -> reportService.deleteReportById(anyLong()));
        // then
        assertThat(result).isExactlyInstanceOf(ReportNotFoundExeption.class);
        verify(reportRepository, times(1)).findById(anyLong());
        verify(reportRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void deleteAllReports_callsReportRepository() {
        // given

        // when
        reportService.deleteAllReports();
        // then
        verify(reportRepository, times(1)).deleteAll();
    }

    @Test
    void saveReport_callsReportRepository() {
        // given
        when(starWarsApiService.getStarWarsApiResponseData(any(QueryCriteria.class))).thenReturn(new StarWarsApiResponseData());
        QueryCriteria queryCriteria = new QueryCriteria("Luke Skywalker", "Tatooine");
        // when
        reportService.saveReport(1L, queryCriteria);
        // then
        verify(reportRepository, times(1)).save(any(Report.class));
    }
}
