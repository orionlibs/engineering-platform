package io.github.orionlibs.core.database.store_and_forward;

import io.github.orionlibs.core.OrionEnumeration;

public enum DatabaseUpdateEventType implements OrionEnumeration
{
    SAVE,
    UPDATE,
    DELETE;


    @Override
    public String get()
    {
        return null;
    }


    @Override
    public boolean is(OrionEnumeration other)
    {
        return other instanceof DatabaseUpdateEventType && this == other;
    }


    @Override
    public boolean isNot(OrionEnumeration other)
    {
        return other instanceof DatabaseUpdateEventType && this != other;
    }


    public static boolean valueExists(String other)
    {
        DatabaseUpdateEventType[] values = values();
        for(DatabaseUpdateEventType value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static DatabaseUpdateEventType getEnumForValue(String other)
    {
        throw new UnsupportedOperationException();
    }
}
