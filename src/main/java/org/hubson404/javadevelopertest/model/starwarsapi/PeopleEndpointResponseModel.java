package org.hubson404.javadevelopertest.model.starwarsapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeopleEndpointResponseModel {

    private String next;
    private List<CharacterModel> results;

}
