package org.hubson404.javadevelopertest.model.starwarsapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StarWarsApiResponseData {

    CharacterModel character;
    PlanetModel planetModel;
    List<FilmModel> filmModels;
}
