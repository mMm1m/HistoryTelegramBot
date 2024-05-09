package ru.example.service.enums;

public enum ServiceCommands {
    ANCIENT("ancient"),
    MIDDLEAGE("middleage"),
    NEWTIME("newtime"),
    MODERNTIME("moderntime");
    private String value;
    @Override
    public String toString()
    {
        return value;
    }
    ServiceCommands(String value)
    {
        this.value = value;
    }
}
