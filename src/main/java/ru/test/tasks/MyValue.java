package ru.test.tasks;

public class MyValue
{
    private Integer a;
    private Integer b;

    public MyValue(Integer a, Integer b)
    {
        this.a = a;
        this.b = b;
    }

    public int hashCode()
    {
        return a.intValue();
    }

    public boolean equals(MyValue o)
    {
        if (this == o) return true;
        if (a == o.a && b == o.b) return true;
        if (a != null && a.equals(o.a) && b != null && b.equals(o.b)) return true;
        return false;
    }
}
