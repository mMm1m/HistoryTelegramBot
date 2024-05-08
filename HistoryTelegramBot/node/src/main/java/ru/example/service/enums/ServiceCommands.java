package ru.example.service.enums;


public enum ServiceCommands {
    START("/start"),
    HELP("/help"),
    NEXT("/next"),
    END("/end");

    private final String cmd;

    ServiceCommands(String val)
    {
        this.cmd = val;
    }

    @Override
    public String toString()
    {
        return cmd;
    }

    public ServiceCommands fromCommand(String string)
    {
        for(var a: ServiceCommands.values())
        {
            if(a.cmd.equals(string)) return a;
        }
        return null;
    }
}
