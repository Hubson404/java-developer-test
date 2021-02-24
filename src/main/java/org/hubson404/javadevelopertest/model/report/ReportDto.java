package org.hubson404.javadevelopertest.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {

    private Long reportId;
    private String queryCriteriaCharacterPhrase;
    private String queryCriteriaPlanetName;
    private List<QueryResultDto> result;

}
