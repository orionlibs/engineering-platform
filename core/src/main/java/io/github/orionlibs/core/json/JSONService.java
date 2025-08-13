package io.github.orionlibs.core.json;

import com.nimbusds.jose.shaded.gson.Gson;

public class JSONService
{
    /*private final ObjectMapper objectMapper;


    public JSONService(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }*/


    public static String convertObjectToJSON(Object objectToConvert)
    {
        return new Gson().toJson(objectToConvert);
    }


    public static Object convertJSONToObject(String JSONData, Class<?> classToConvertTo)
    {
        return new Gson().fromJson(JSONData, classToConvertTo);
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
