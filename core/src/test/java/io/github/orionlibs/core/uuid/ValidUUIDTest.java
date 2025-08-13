package io.github.orionlibs.core.uuid;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ValidUUIDTest
{
    private static ValidatorFactory vf;
    private static Validator validator;


    @BeforeAll
    static void setupValidator()
    {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }


    @AfterAll
    static void closeFactory()
    {
        vf.close();
    }


    @Test
    void validUUID_valid()
    {
        Class1 temp = new Class1();
        temp.uuid = UUID.randomUUID().toString();
        Set<ConstraintViolation<Class1>> violations = validator.validate(temp);
        assertThat(violations.isEmpty()).isTrue();
    }


    @Test
    void validUUID_invalid()
    {
        Class1 temp = new Class1();
        temp.uuid = "1234";
        Set<ConstraintViolation<Class1>> violations = validator.validate(temp);
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<Class1> v = violations.iterator().next();
        assertThat("uuid").isEqualTo(v.getPropertyPath().toString());
        assertThat("The value is not a valid UUID").isEqualTo(v.getMessage());
    }


    public static class Class1
    {
        @ValidUUID public String uuid;
    }
}
