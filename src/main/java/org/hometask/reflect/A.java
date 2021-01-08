package org.hometask.reflect;

import org.hometask.ReflectClass;

@ReflectClass
public class A {
    private String field="field A";

    @Override
    public String toString() {
        return "A{" +
                "field='" + field + '\'' +
                '}';
    }
}
