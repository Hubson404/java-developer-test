package org.hubson404.javadevelopertest.model.starwarsapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCriteria {

    @NotNull(message = "Value cannot be null")
    @JsonProperty(value = "query_criteria_character_phrase")
    private String characterPhrase;

    @NotNull(message = "Value cannot be null")
    @JsonProperty(value = "query_criteria_planet_name")
    private String planetName;

}
