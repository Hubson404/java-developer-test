package org.hubson404.javadevelopertest.repositories;

import org.hubson404.javadevelopertest.domain.QueryResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryResultRepository extends JpaRepository<QueryResult, Long> {
}
