package org.hubson404.javadevelopertest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hubson404.javadevelopertest.domain.Report;
import org.hubson404.javadevelopertest.model.starwarsapi.QueryCriteria;
import org.hubson404.javadevelopertest.repositories.QueryResultRepository;
import org.hubson404.javadevelopertest.repositories.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReportControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    QueryResultRepository queryResultRepository;

    @Autowired
    ReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        queryResultRepository.deleteAll();
        reportRepository.deleteAll();
    }

    @Test
    void saveReportToDatabase_createsNewReportAndReturnsStatusCode204() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 1;
        QueryCriteria qc = new QueryCriteria("Luke Skywalker", "Tatooine");

        String requestBody = objectMapper.writeValueAsString(qc);
        MockHttpServletRequestBuilder put = put("/reports/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        // when
        MvcResult result = mockMvc.perform(put).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        List<Report> reports = reportRepository.findAll();
        assertThat(reports.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void saveReportToDatabase_QueryCriteriaCharacterPhraseFiledIsNull_throwsExceptionAndReturnsStatusCode400() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 0;
        QueryCriteria qc = new QueryCriteria(null, "Tatooine");

        String requestBody = objectMapper.writeValueAsString(qc);
        MockHttpServletRequestBuilder put = put("/reports/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        // when
        MvcResult result = mockMvc.perform(put).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Report> reports = reportRepository.findAll();
        assertThat(reports.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void saveReportToDatabase_QueryCriteriaPlanetNameFiledIsNull_throwsExceptionAndReturnsStatusCode400() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 0;
        QueryCriteria qc = new QueryCriteria("Luke Skywalker", null);

        String requestBody = objectMapper.writeValueAsString(qc);
        MockHttpServletRequestBuilder put = put("/reports/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        // when
        MvcResult result = mockMvc.perform(put).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Report> reports = reportRepository.findAll();
        assertThat(reports.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void saveReportToDatabase_characterByGivenQueryCriteriaDoesNotExist_ReturnsStatusCode404() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 0;
        QueryCriteria qc = new QueryCriteria("Not Luke Skywalker", "Tatooine");

        String requestBody = objectMapper.writeValueAsString(qc);
        MockHttpServletRequestBuilder put = put("/reports/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        // when
        MvcResult result = mockMvc.perform(put).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        List<Report> reports = reportRepository.findAll();
        assertThat(reports.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void saveReportToDatabase_queriedPlanetNameDoesNotMatchCharactersHomeworld_ReturnsStatusCode400() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 0;
        QueryCriteria qc = new QueryCriteria("Luke Skywalker", "Not Tatooine");

        String requestBody = objectMapper.writeValueAsString(qc);
        MockHttpServletRequestBuilder put = put("/reports/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        // when
        MvcResult result = mockMvc.perform(put).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        List<Report> reports = reportRepository.findAll();
        assertThat(reports.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void findAllReports_returnsReportWrapperAndStatusCode200() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            reportRepository.save(Report.builder().reportId((long) i).build());
        }
        MockHttpServletRequestBuilder get = get("/reports");
        // when
        MvcResult result = mockMvc.perform(get).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Report> reports = reportRepository.findAll();
        assertThat(reports.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void findReportById_returnsReportDtoAndStatusCode200() throws Exception {
        // given
        Report savedReport = reportRepository.save(Report.builder().reportId(1L).build());
        Long savedReportId = savedReport.getReportId();
        // when
        MockHttpServletRequestBuilder get = get("/reports/" + savedReportId);
        MvcResult result = mockMvc.perform(get).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Optional<Report> reportById = reportRepository.findById(savedReportId);
        assertThat(reportById.isPresent()).isTrue();
    }

    @Test
    void deleteReportFromDatabase_deletesReportAndReturnStatusCode204() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 3;
        for (int i = 0; i < expectedNumberOfEntriesInRepository; i++) {
            reportRepository.save(Report.builder().reportId((long) i).build());
        }
        Report savedReport = reportRepository.save(Report.builder().reportId(50L).build());
        Long savedReportId = savedReport.getReportId();
        // when
        MockHttpServletRequestBuilder delete = delete("/reports/" + savedReportId);
        MvcResult result = mockMvc.perform(delete).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        List<Report> reports = reportRepository.findAll();
        assertThat(reports.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }

    @Test
    void deleteAllReportsFromDatabase_deletesAllReportsAndReturnStatusCode204() throws Exception {
        // given
        int expectedNumberOfEntriesInRepository = 0;
        int initialNumberOfEntriesInRepository = 3;
        for (int i = 0; i < initialNumberOfEntriesInRepository; i++) {
            reportRepository.save(Report.builder().reportId((long) i).build());
        }
        // when
        MockHttpServletRequestBuilder delete = delete("/reports");
        MvcResult result = mockMvc.perform(delete).andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        List<Report> reports = reportRepository.findAll();
        assertThat(reports.size()).isEqualTo(expectedNumberOfEntriesInRepository);
    }
}
