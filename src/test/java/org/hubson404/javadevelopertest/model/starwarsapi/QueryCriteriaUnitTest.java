package org.hubson404.javadevelopertest.model.starwarsapi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueryCriteriaUnitTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void instantiateQueryCriteria_whenFieldPlanetNameIsNull_ThrowException() {
        //given
        QueryCriteria qc = new QueryCriteria("Luke Skywalker", null);
        //when
        Set<ConstraintViolation<QueryCriteria>> violations = validator.validate(qc);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void instantiateQueryCriteria_whenFieldCharacterPhraseIsNull_ThrowException() {
        //given
        QueryCriteria qc = new QueryCriteria(null, "Tatooine");
        //when
        Set<ConstraintViolation<QueryCriteria>> violations = validator.validate(qc);
        //then
        assertFalse(violations.isEmpty());
    }

    @Test
    void instantiateQueryCriteria_createsQueryCriteria() {
        //given
        QueryCriteria qc = new QueryCriteria("Luke Skywalker", "Tatooine");
        //when
        Set<ConstraintViolation<QueryCriteria>> violations = validator.validate(qc);
        //then
        assertTrue(violations.isEmpty());
    }

}
