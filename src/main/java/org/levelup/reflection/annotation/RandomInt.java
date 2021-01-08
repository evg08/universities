package org.levelup.reflection.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
//@Target({ElementType.FIELD,ElementType.PARAMETER})
//PARAMETER target
// -void method(@RandomInt int value)
// -void Book(@RandomInt int bookNumber)  constructor

@Retention(RetentionPolicy.RUNTIME)
public @interface RandomInt {
    int min() default 0;
    int max() default Integer.MAX_VALUE;

}
