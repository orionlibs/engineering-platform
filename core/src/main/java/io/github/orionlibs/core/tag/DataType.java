package io.github.orionlibs.core.tag;

import io.github.orionlibs.core.OrionEnumeration;

public enum DataType implements OrionEnumeration
{
    STRING("1"),
    INTEGER("2"),
    LONG("3"),
    FLOAT("4"),
    DOUBLE("5"),
    BOOLEAN("6"),
    DATETIME("7");
    private String name;


    private DataType(String name)
    {
        setName(name);
    }


    @Override
    public String get()
    {
        return getName();
    }


    public int getAsInt()
    {
        return Integer.parseInt(getName());
    }


    public String getName()
    {
        return this.name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    @Override
    public boolean is(OrionEnumeration other)
    {
        return other instanceof DataType && this == other;
    }


    @Override
    public boolean isNot(OrionEnumeration other)
    {
        return other instanceof DataType && this != other;
    }


    public static boolean valueExists(String other)
    {
        DataType[] values = values();
        for(DataType value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static DataType getEnumForValue(String other)
    {
        DataType[] values = values();
        for(DataType value : values)
        {
            if(value.get().equals(other))
            {
                return value;
            }
        }
        return null;
    }
}
