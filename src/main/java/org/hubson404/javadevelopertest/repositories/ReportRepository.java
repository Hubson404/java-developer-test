package org.hubson404.javadevelopertest.repositories;

import org.hubson404.javadevelopertest.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
