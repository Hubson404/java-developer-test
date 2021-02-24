package org.hubson404.javadevelopertest.model.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportWrapper {

    private List<ReportDto> reports;

}
