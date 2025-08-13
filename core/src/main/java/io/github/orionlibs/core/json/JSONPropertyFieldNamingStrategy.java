package io.github.orionlibs.core.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jose.shaded.gson.FieldNamingStrategy;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JSONPropertyFieldNamingStrategy implements FieldNamingStrategy
{
    @Override
    public String translateName(Field f)
    {
        JsonProperty jp = f.getAnnotation(JsonProperty.class);
        if(jp != null && jp.value() != null && !jp.value().isEmpty())
        {
            return jp.value();
        }
        String capitalized = Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
        String[] candidates = new String[] {"get" + capitalized, "is" + capitalized};
        for(String name : candidates)
        {
            try
            {
                Method m = f.getDeclaringClass().getMethod(name);
                JsonProperty jp2 = m.getAnnotation(JsonProperty.class);
                if(jp2 != null && jp2.value() != null && !jp2.value().isEmpty())
                {
                    return jp2.value();
                }
            }
            catch(NoSuchMethodException ignored)
            {
            }
        }
        return f.getName();
    }


    public static Gson createGsonWithJsonPropertySupport()
    {
        return new GsonBuilder()
                        .setFieldNamingStrategy(new JSONPropertyFieldNamingStrategy())
                        .create();
    }
}
