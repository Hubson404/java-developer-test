package org.hubson404.javadevelopertest.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryResultDto {

    private Long filmId;
    private String filmName;
    private Long characterId;
    private String characterName;
    private Long planetId;
    private String planetName;

}
