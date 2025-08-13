package io.github.orionlibs.core.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class JSONService
{
    private static ObjectMapper mapper;

    static
    {
        mapper = new Jackson2ObjectMapperBuilder().serializationInclusion(Include.NON_NULL)
                        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                                        SerializationFeature.FAIL_ON_EMPTY_BEANS,
                                        SerializationFeature.FAIL_ON_SELF_REFERENCES)
                        .build();
        mapper = mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    public static String convertObjectToJSON(Object objectToConvert)
    {
        try
        {
            return mapper.writeValueAsString(objectToConvert);
        }
        catch(JsonProcessingException e)
        {
            return "";
        }
        //Gson gson = JSONPropertyFieldNamingStrategy.createGsonWithJsonPropertySupport();
        /*Gson gson = new GsonBuilder()
                        .registerTypeAdapterFactory(new JSONPropertyTypeAdapterFactory())
                        // (optionally) register type adapters for java.time, etc.
                        .create();*/
        //return gson.toJson(objectToConvert);
    }


    public static Object convertJSONToObject(String JSONData, Class<?> classToConvertTo)
    {
        try
        {
            return mapper.readValue(JSONData, classToConvertTo);
        }
        catch(JsonProcessingException e)
        {
            return "";
        }
        //Gson gson = JSONPropertyFieldNamingStrategy.createGsonWithJsonPropertySupport();
        /*Gson gson = new GsonBuilder()
                        .registerTypeAdapterFactory(new JSONPropertyTypeAdapterFactory())
                        // (optionally) register type adapters for java.time, etc.
                        .create();*/
        //return gson.fromJson(JSONData, classToConvertTo);
    }


    /*public String toJson(Object pojo)
    {
        try
        {
            return objectMapper.writeValueAsString(pojo);
        }
        catch(JsonProcessingException e)
        {
            throw new IllegalStateException("Failed to serialize object to JSON", e);
        }
    }


    public Object fromJson(String json, Class<?> aClass)
    {
        try
        {
            return objectMapper.readValue(json, aClass);
        }
        catch(JsonProcessingException e)
        {
            throw new IllegalStateException("Failed to serialize object to JSON", e);
        }
    }*/
}
