package org.hubson404.javadevelopertest.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hubson404.javadevelopertest.exceptions.InsufficientQueryDataException;
import org.hubson404.javadevelopertest.model.starwarsapi.QueryCriteria;
import org.hubson404.javadevelopertest.model.starwarsapi.StarWarsApiResponseData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
class StarWarsApiServiceIntegrationTest {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StarWarsApiService starWarsApiService;


    @Test
    void getStarWarsApiResponseData_whenQueryCriteriaFieldPlanetNameIsNull_ThrowsException() {

        //given
        QueryCriteria qc = new QueryCriteria("Luke Skywalker", null);
        //when
        Throwable result = catchThrowable(() -> starWarsApiService.getStarWarsApiResponseData(qc));
        //then
        assertThat(result).isExactlyInstanceOf(InsufficientQueryDataException.class);
    }

    @Test
    void getStarWarsApiResponseData_whenQueryCriteriaFieldCharacterPhraseIsNull_ThrowsException() {

        //given
        QueryCriteria qc = new QueryCriteria(null, "Tatooine");
        //when
        Throwable result = catchThrowable(() -> starWarsApiService.getStarWarsApiResponseData(qc));
        //then
        assertThat(result).isExactlyInstanceOf(InsufficientQueryDataException.class);
    }

    @Test
    void getStarWarsApiResponseData_returnsStarWarsApiResponseData() {

        //given
        QueryCriteria qc = new QueryCriteria("Luke Skywalker", "Tatooine");
        //when
        StarWarsApiResponseData result = starWarsApiService.getStarWarsApiResponseData(qc);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getPlanetModel().getName()).isEqualTo(qc.getPlanetName());
        assertThat(result.getCharacter().getName()).isEqualTo(qc.getCharacterPhrase());
    }
}
