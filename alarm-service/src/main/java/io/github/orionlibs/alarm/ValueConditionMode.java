package io.github.orionlibs.alarm;

import io.github.orionlibs.core.OrionEnumeration;

public enum ValueConditionMode implements OrionEnumeration
{
    EQUALS(1),
    NOT_EQUALS(2),
    GREATER_THAN_SETPOINT(3),
    GREATER_OR_EQUAL_TO_SETPOINT(4),
    LESS_THAN_SETPOINT(5),
    LESS_OR_EQUAL_TO_SETPOINT(6),
    ANY_CHANGE(7);
    private int name;


    private ValueConditionMode(int name)
    {
        setName(name);
    }


    @Override
    public String get()
    {
        return Integer.toString(getName());
    }


    public int getAsInt()
    {
        return getName();
    }


    public int getName()
    {
        return this.name;
    }


    public void setName(int name)
    {
        this.name = name;
    }


    @Override
    public boolean is(OrionEnumeration other)
    {
        return other instanceof ValueConditionMode && this == other;
    }


    @Override
    public boolean isNot(OrionEnumeration other)
    {
        return other instanceof ValueConditionMode && this != other;
    }


    public static boolean valueExists(String other)
    {
        ValueConditionMode[] values = values();
        for(ValueConditionMode value : values)
        {
            if(value.get().equals(other))
            {
                return true;
            }
        }
        return false;
    }


    public static ValueConditionMode getEnumForValue(String other)
    {
        ValueConditionMode[] values = values();
        for(ValueConditionMode value : values)
        {
            if(value.get().equals(other))
            {
                return value;
            }
        }
        return null;
    }


    public static String getEnumNameForIntegerValue(int valueTemp)
    {
        ValueConditionMode[] values = values();
        for(ValueConditionMode value : values)
        {
            if(value.getAsInt() == valueTemp)
            {
                return value.name();
            }
        }
        return null;
    }
}
