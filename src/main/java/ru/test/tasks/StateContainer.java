package ru.test.tasks;

import java.util.Map;

class StateContainer
{
    private final Map<String,MyObject> state;

    public StateContainer(Map<String, MyObject> state)
    {

        this.state = state;
    }

    public MyObject updateObject(String id, MyObject o)
    {
        return state.put(id, o);
    }

    public MyObject getObject(String id)
    {
        return state.get(id);
    }

}