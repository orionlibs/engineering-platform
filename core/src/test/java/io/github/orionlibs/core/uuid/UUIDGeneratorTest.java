package io.github.orionlibs.core.uuid;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class UUIDGeneratorTest
{
    @Test
    void generateUUID()
    {
        Class1 temp = new Class1();
        assertThat(temp.generateUUID().length()).isGreaterThan(20);
    }


    public static class Class1 implements UUIDGenerator
    {
    }
}
