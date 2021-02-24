package org.hubson404.javadevelopertest.model.report;

import lombok.RequiredArgsConstructor;
import org.hubson404.javadevelopertest.domain.Report;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReportMapper {

    private final QueryResultMapper queryResultMapper;

    public ReportDto toReportDto(Report report) {
        return ReportDto.builder()
                .reportId(report.getReportId())
                .queryCriteriaCharacterPhrase(report.getQueryCriteriaCharacterPhrase())
                .queryCriteriaPlanetName(report.getQueryCriteriaPlanetName())
                .result(report.getResult().stream().map(queryResultMapper::toQueryResultDto).collect(Collectors.toList()))
                .build();

    }

}
