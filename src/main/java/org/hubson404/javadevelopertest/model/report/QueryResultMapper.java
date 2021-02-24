package org.hubson404.javadevelopertest.model.report;

import org.hubson404.javadevelopertest.domain.QueryResult;
import org.springframework.stereotype.Component;

@Component
public class QueryResultMapper {

    public QueryResultDto toQueryResultDto(QueryResult queryResult) {
        return QueryResultDto.builder()
                .filmId(queryResult.getFilmId())
                .filmName(queryResult.getFilmName())
                .characterId(queryResult.getCharacterId())
                .characterName(queryResult.getCharacterName())
                .planetId(queryResult.getPlanetId())
                .planetName(queryResult.getPlanetName())
                .build();
    }
}
