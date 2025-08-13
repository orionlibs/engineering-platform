package io.github.orionlibs.user.password;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class PasswordTest
{
    @Test
    void password_valid()
    {
        Environment env = mock(Environment.class);
        when(env.getProperty("password.pattern", "^(?=.{8,})(?=.*[A-Z])(?=.*\\d).*$")).thenReturn("^(?=.{8,})(?=.*[A-Z])(?=.*\\d).*$");
        PasswordConstraintValidator validator = new PasswordConstraintValidator(env);
        assertThat(validator.isValid("Abcdef12", null)).isTrue();
        assertThat(validator.isValid("abcdef12", null)).isFalse();
        assertThat(validator.isValid("Abcdefgh", null)).isFalse();
    }


    @Test
    void password_invalid()
    {
        Environment env = mock(Environment.class);
        when(env.getProperty(anyString(), anyString())).thenReturn(".*");
        PasswordConstraintValidator validator = new PasswordConstraintValidator(env);
        assertThat(validator.isValid(null, null)).isFalse();
        assertThat(validator.isValid("  ", null)).isFalse();
    }
    /*@Test
    void password_valid()
    {
        Class1 temp = new Class1();
        temp.password = "bunkzh3Z!";
        Set<ConstraintViolation<Class1>> violations = validator.validate(temp);
        assertThat(violations.isEmpty()).isTrue();
    }


    @Test
    void password_invalid()
    {
        Class1 temp = new Class1();
        temp.password = "1234";
        Set<ConstraintViolation<Class1>> violations = validator.validate(temp);
        assertThat(violations.size()).isEqualTo(1);
        ConstraintViolation<Class1> v = violations.iterator().next();
        assertThat("password").isEqualTo(v.getPropertyPath().toString());
        assertThat("Password does not meet security requirements").isEqualTo(v.getMessage());
    }*/


    public static class Class1
    {
        @Password public String password;
    }
}
