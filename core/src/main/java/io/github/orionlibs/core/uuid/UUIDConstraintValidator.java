package io.github.orionlibs.core.uuid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UUIDConstraintValidator implements ConstraintValidator<ValidUUID, String>
{
    @Override
    public void initialize(ValidUUID constraintAnnotation)
    {
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        if(value == null || value.isBlank())
        {
            return false;
        }
        try
        {
            UUID.fromString(value);
            return true;
        }
        catch(IllegalArgumentException e)
        {
            return false;
        }
    }
}
