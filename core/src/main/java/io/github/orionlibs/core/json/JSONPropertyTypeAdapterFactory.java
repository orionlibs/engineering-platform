package io.github.orionlibs.core.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.nimbusds.jose.shaded.gson.TypeAdapter;
import com.nimbusds.jose.shaded.gson.TypeAdapterFactory;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import com.nimbusds.jose.shaded.gson.stream.JsonReader;
import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JSONPropertyTypeAdapterFactory implements TypeAdapterFactory
{
    /**
     * Build a mapping from the Java field/component/getter name -> annotated @JsonProperty value (if present).
     */
    private static Map<String, String> buildFieldToJsonNameMap(Class<?> raw)
    {
        Map<String, String> map = new LinkedHashMap<>();
        // 1) Record components (if record)
        if(raw.isRecord())
        {
            for(RecordComponent rc : raw.getRecordComponents())
            {
                JsonProperty ann = rc.getAnnotation(JsonProperty.class);
                if(ann != null && ann.value() != null && !ann.value().isEmpty())
                {
                    map.put(rc.getName(), ann.value());
                }
            }
        }
        // 2) Fields including superclasses
        for(Field f : getAllFields(raw))
        {
            JsonProperty ann = f.getAnnotation(JsonProperty.class);
            if(ann != null && ann.value() != null && !ann.value().isEmpty())
            {
                map.put(f.getName(), ann.value());
                continue;
            }
            // 3) Check getter method for annotation
            Method getter = findGetterForField(raw, f);
            if(getter != null)
            {
                JsonProperty onGetter = getter.getAnnotation(JsonProperty.class);
                if(onGetter != null && onGetter.value() != null && !onGetter.value().isEmpty())
                {
                    map.put(f.getName(), onGetter.value());
                }
            }
        }
        return map;
    }


    private static List<Field> getAllFields(Class<?> type)
    {
        List<Field> fields = new ArrayList<>();
        Class<?> cur = type;
        while(cur != null && cur != Object.class)
        {
            for(Field f : cur.getDeclaredFields())
            {
                // Ignore synthetic/backing fields often created by compiler
                if(!f.isSynthetic())
                {
                    fields.add(f);
                }
            }
            cur = cur.getSuperclass();
        }
        return fields;
    }


    private static Method findGetterForField(Class<?> type, Field field)
    {
        // look for conventional getter names getX or isX
        String name = field.getName();
        String capital = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        String[] candidates = new String[] {"get" + capital, "is" + capital, name}; // add raw name (for public fields as accessor)
        for(String g : candidates)
        {
            try
            {
                Method m = type.getMethod(g);
                if(m.getParameterCount() == 0)
                {
                    return m;
                }
            }
            catch(NoSuchMethodException ignored)
            {
            }
        }
        return null;
    }


    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
    {
        Class<? super T> raw = type.getRawType();
        // Skip primitives, JsonElement and core Java classes
        if(raw.isPrimitive() || raw == String.class || Number.class.isAssignableFrom(raw)
                        || raw == Boolean.class || raw == Character.class || raw == JsonElement.class)
        {
            return null;
        }
        // Build mapping from java field/component name -> JsonProperty name
        Map<String, String> fieldToJson = buildFieldToJsonNameMap(raw);
        if(fieldToJson.isEmpty())
        {
            return null; // no special mapping needed
        }
        // Reverse map: jsonName -> fieldName for deserialization
        final Map<String, String> jsonToField = new HashMap<>();
        fieldToJson.forEach((k, v) -> jsonToField.put(v, k));
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        return new TypeAdapter<T>()
        {
            /*@Override
            public void write(JsonWriter out, T value) throws IOException
            {
                if(value == null)
                {
                    out.nullValue();
                    return;
                }
                // Let Gson produce a JsonElement
                JsonElement tree = delegate.toJsonTree(value);
                if(tree.isJsonObject())
                {
                    JsonObject srcObj = tree.getAsJsonObject();
                    JsonObject dstObj = new JsonObject();
                    for(Map.Entry<String, JsonElement> e : srcObj.entrySet())
                    {
                        String srcKey = e.getKey();
                        // If we have an annotated mapping for a field named srcKey, use that annotation name.
                        // Note: we expect delegate used field name equal to the Java field name.
                        String mapped = fieldToJson.getOrDefault(srcKey, srcKey);
                        dstObj.add(mapped, e.getValue());
                    }
                    gson.toJson(dstObj, out);
                }
                else
                {
                    // Not an object — let delegate write normally
                    delegate.write(out, value);
                }
            }*/


            @Override
            @SuppressWarnings("unchecked")
            public void write(JsonWriter jsonWriter, Object value) throws IOException
            {
                if(value == null)
                {
                    jsonWriter.nullValue();
                    return;
                }
                // Let Gson produce a JsonElement
                JsonElement tree = delegate.toJsonTree((T)value);
                if(tree.isJsonObject())
                {
                    JsonObject srcObj = tree.getAsJsonObject();
                    JsonObject dstObj = new JsonObject();
                    for(Map.Entry<String, JsonElement> e : srcObj.entrySet())
                    {
                        String srcKey = e.getKey();
                        // If we have an annotated mapping for a field named srcKey, use that annotation name.
                        // Note: we expect delegate used field name equal to the Java field name.
                        String mapped = fieldToJson.getOrDefault(srcKey, srcKey);
                        dstObj.add(mapped, e.getValue());
                    }
                    gson.toJson(dstObj, jsonWriter);
                }
                else
                {
                    // Not an object — let delegate write normally
                    delegate.write(jsonWriter, (T)value);
                }
            }


            @Override
            public T read(JsonReader in) throws IOException
            {
                JsonElement tree = JsonParser.parseReader(in);
                if(tree.isJsonObject())
                {
                    JsonObject src = tree.getAsJsonObject();
                    JsonObject transformed = new JsonObject();
                    for(Map.Entry<String, JsonElement> e : src.entrySet())
                    {
                        String jsonKey = e.getKey();
                        // If incoming JSON used the annotated name, map it back to the field name:
                        String mappedField = jsonToField.getOrDefault(jsonKey, jsonKey);
                        transformed.add(mappedField, e.getValue());
                    }
                    return delegate.fromJsonTree(transformed);
                }
                else
                {
                    return delegate.fromJsonTree(tree);
                }
            }
        }.nullSafe();
    }
}
