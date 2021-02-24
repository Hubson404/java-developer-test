package org.hubson404.javadevelopertest.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hubson404.javadevelopertest.exceptions.*;
import org.hubson404.javadevelopertest.model.starwarsapi.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StarWarsApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public StarWarsApiResponseData getStarWarsApiResponseData(QueryCriteria qc) {
        CharacterModel character = findCharacterByName(qc.getCharacterPhrase());
        PlanetModel planetModel = getPlanetModel(qc, character);
        List<FilmModel> filmModels = findFilmModelsByUrls(character.getFilms());

        return StarWarsApiResponseData.builder()
                .character(character)
                .filmModels(filmModels)
                .planetModel(planetModel)
                .build();
    }

    private CharacterModel findCharacterByName(String name) {
        if (name == null) {
            throw new InsufficientQueryDataException("CHARACTER_PHRASE value is mandatory");
        }
        CharacterModel characterModel = getCharacterModel(name);
        return characterModel;
    }

    private CharacterModel getCharacterModel(String name) {
        int pageNumber = 1;
        CharacterModel characterModel = null;

        while (name != null && characterModel == null) {
            PeopleEndpointResponseModel apiResponse = getStarWarsApiResponse(pageNumber);
            Optional<CharacterModel> characterModelOptional = getCharacterModel(name, apiResponse);

            if (characterModelOptional.isPresent()) {
                characterModel = characterModelOptional.get();
            }
            if (apiResponse.getNext() != null) {
                pageNumber++;
            } else {
                throw new CharacterNotFoundException(name);
            }
        }
        return characterModel;
    }

    private PeopleEndpointResponseModel getStarWarsApiResponse(int pageNumber) {
        URI generatedUri = generateUriForPageNumber(pageNumber);
        String responseBody = getResponseBody(generatedUri);
        return mapResponseBodyToPeopleEndpointResponseModel(responseBody);
    }

    private URI generateUriForPageNumber(Integer pageNumber) {
        UriComponents build = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/api/people/")
                .queryParam("page", pageNumber)
                .build();
        return build.toUri();
    }

    private String getResponseBody(URI requestUri) {
        ResponseEntity<String> responseEntity = getStringResponseEntity(requestUri);
        return responseEntity.getBody();
    }

    private ResponseEntity<String> getStringResponseEntity(URI uri) {
        try {
            return restTemplate.getForEntity(uri, String.class);

        } catch (RestClientException exception) {
            throw new ExternalApiConnectionFailureException("Unable to get data from remote service.");
        }
    }

    private PeopleEndpointResponseModel mapResponseBodyToPeopleEndpointResponseModel(String responseBody) {
        PeopleEndpointResponseModel peopleEndpointResponseModel;
        try {
            peopleEndpointResponseModel = objectMapper.readValue(responseBody, PeopleEndpointResponseModel.class);
        } catch (JsonProcessingException e) {
            throw new DataProcessingErrorException("Unable to process peopleResponseModel data.");
        }
        return peopleEndpointResponseModel;
    }

    private Optional<CharacterModel> getCharacterModel(String name, PeopleEndpointResponseModel apiResponse) {
        return apiResponse
                .getResults()
                .stream()
                .filter(character -> character.getName().equals(name))
                .findFirst();
    }

    private PlanetModel getPlanetModel(QueryCriteria qc, CharacterModel character) {
        if (qc.getPlanetName() == null) {
            throw new InsufficientQueryDataException("PLANET_NAME value is mandatory");
        }
        PlanetModel planetModel = findPlanetByUrl(character.getHomeworld());

        if (!planetModel.getName().equals(qc.getPlanetName())) {
            throw new IncorrectQueryDataException("Given PLANET_NAME doesn't match found CHARACTERS HOMEWORLD.");
        }
        return planetModel;
    }

    private PlanetModel findPlanetByUrl(String homeworldUrl) {
        String responseBody = getStringResponseBodyFromUrl(homeworldUrl);
        PlanetModel planetModel = mapResponseBodyToPlanetModel(responseBody);
        return planetModel;
    }

    private URI generateUriFromUrl(String uriString) {
        UriComponents build = UriComponentsBuilder
                .fromHttpUrl(uriString)
                .build();
        return build.toUri();
    }

    private PlanetModel mapResponseBodyToPlanetModel(String responseBody) {
        PlanetModel planetModel;
        try {
            planetModel = objectMapper.readValue(responseBody, PlanetModel.class);
        } catch (JsonProcessingException e) {
            throw new DataProcessingErrorException("Unable to process planetModel data.");
        }
        return planetModel;
    }

    private List<FilmModel> findFilmModelsByUrls(List<String> filmUrls) {
        List<FilmModel> filmModels = new ArrayList<>();
        filmUrls.forEach(filmUrl -> {
            String responseBody = getStringResponseBodyFromUrl(filmUrl);
            filmModels.add(mapResponseBodyToFilmModel(responseBody));
        });
        return filmModels;
    }

    private FilmModel mapResponseBodyToFilmModel(String responseBody) {
        FilmModel filmModel;
        try {
            filmModel = objectMapper.readValue(responseBody, FilmModel.class);
        } catch (JsonProcessingException e) {
            throw new DataProcessingErrorException("Unable to process filmModel data.");
        }
        return filmModel;
    }

    private String getStringResponseBodyFromUrl(String filmUrl) {
        URI requestUri = generateUriFromUrl(filmUrl);
        return getResponseBody(requestUri);
    }


}
