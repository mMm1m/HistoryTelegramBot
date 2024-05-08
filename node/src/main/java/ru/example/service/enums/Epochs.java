package ru.example.service.enums;

public enum Epochs {
    Ancient("/ancient"),
    MiddleAges("/middleages"),
    NewTime("/newtime"),
    ModernTime("/moderntime");

    private final String value;

    Epochs(String val)
    {
        this.value = val;
    }

    @Override
    public String toString()
    {
        return value;
    }

    public static Epochs fromValue(String string)
    {
        for(var a: Epochs.values())
        {
            if(a.value.equals(string)) return a;
        }
        return null;
    }
}
