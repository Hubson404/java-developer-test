package org.hubson404.javadevelopertest.model.starwarsapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterModel {

    private String name;
    private String homeworld;
    private List<String> films;
    private String url;

}
