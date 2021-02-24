package org.hubson404.javadevelopertest.services;

import lombok.RequiredArgsConstructor;
import org.hubson404.javadevelopertest.domain.QueryResult;
import org.hubson404.javadevelopertest.domain.Report;
import org.hubson404.javadevelopertest.exceptions.ReportNotFoundExeption;
import org.hubson404.javadevelopertest.model.report.ReportDto;
import org.hubson404.javadevelopertest.model.report.ReportMapper;
import org.hubson404.javadevelopertest.model.report.ReportWrapper;
import org.hubson404.javadevelopertest.model.starwarsapi.FilmModel;
import org.hubson404.javadevelopertest.model.starwarsapi.IndicesExtractor;
import org.hubson404.javadevelopertest.model.starwarsapi.QueryCriteria;
import org.hubson404.javadevelopertest.model.starwarsapi.StarWarsApiResponseData;
import org.hubson404.javadevelopertest.repositories.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final StarWarsApiService SWApiService;

    public ReportWrapper findAllReports() {
        List<Report> allReports = reportRepository.findAll();
        return toReportWrapper(allReports);
    }

    private ReportWrapper toReportWrapper(List<Report> allReports) {
        List<ReportDto> allReportDtos = allReports.stream().map(reportMapper::toReportDto).collect(Collectors.toList());
        return new ReportWrapper(allReportDtos);
    }

    public ReportDto findReportById(Long reportId) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        Report reportById = optionalReport.orElseThrow(() -> new ReportNotFoundExeption(reportId));
        return reportMapper.toReportDto(reportById);
    }

    public void deleteReportById(Long reportId) {
        reportRepository.findById(reportId).ifPresentOrElse(
                report -> reportRepository.deleteById(report.getReportId()),
                () -> {
                    throw new ReportNotFoundExeption(reportId);
                });
    }

    public void deleteAllReports() {
        reportRepository.deleteAll();
    }


    public void saveReport(Long reportId, QueryCriteria qc) {
        List<QueryResult> queryResults = getStarWarsApiQueryResults(qc);
        Report report = generateReport(reportId, qc, queryResults);
        reportRepository.save(report);
    }

    private List<QueryResult> getStarWarsApiQueryResults(QueryCriteria qc) {
        StarWarsApiResponseData apiData = SWApiService.getStarWarsApiResponseData(qc);
        List<QueryResult> queryResults = mapStarWarsApiResponseDataToQueryResults(apiData);
        return queryResults;
    }

    private List<QueryResult> mapStarWarsApiResponseDataToQueryResults(StarWarsApiResponseData apiData) {
        List<QueryResult> queryResults = new ArrayList<>();
        if (apiData.getFilmModels() != null) {
            for (FilmModel filmModel : apiData.getFilmModels()) {
                queryResults.add(QueryResult.builder()
                        .filmId(IndicesExtractor.getIndexFromUrl(filmModel.getUrl()))
                        .filmName(filmModel.getTitle())
                        .characterId(IndicesExtractor.getIndexFromUrl(apiData.getCharacter().getUrl()))
                        .characterName(apiData.getCharacter().getName())
                        .planetId(IndicesExtractor.getIndexFromUrl(apiData.getPlanetModel().getUrl()))
                        .planetName(apiData.getPlanetModel().getName())
                        .build());
            }
        }
        return queryResults;
    }

    private Report generateReport(Long reportId, QueryCriteria qc, List<QueryResult> queryResults) {
        return Report.builder()
                .reportId(reportId)
                .queryCriteriaCharacterPhrase(qc.getCharacterPhrase())
                .queryCriteriaPlanetName(qc.getPlanetName())
                .result(queryResults)
                .build();
    }
}
