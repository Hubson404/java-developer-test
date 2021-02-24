package org.hubson404.javadevelopertest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    private Long reportId;

    private String queryCriteriaCharacterPhrase;
    private String queryCriteriaPlanetName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_report_id")
    private List<QueryResult> result;

}
