package io.github.orionlibs.core.tag;

import io.github.orionlibs.core.OrionEnumeration;

public enum Quality implements OrionEnumeration
{
    GOOD("1"),
    BAD("2");
    private String name;


    private Quality(String name)
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
        return other instanceof Quality && this == other;
    }


    @Override
    public boolean isNot(OrionEnumeration other)
    {
        return other instanceof Quality && this != other;
    }


    public static boolean valueExists(String other)
    {
        Quality[] values = values();
        for(Quality value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static Quality getEnumForValue(String other)
    {
        Quality[] values = values();
        for(Quality value : values)
        {
            if(value.get().equals(other))
            {
                return value;
            }
        }
        return null;
    }
}
