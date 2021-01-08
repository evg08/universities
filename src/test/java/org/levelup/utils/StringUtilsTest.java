package org.levelup.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    //isBlankShouldReturnTrueThenValueIsNull
    @Test
    public void testIsBlank_whenValueIsNull_thenReturnTrue(){
        //given
        //when
        boolean result=StringUtils.isBlank(null);
        //then
        Assertions.assertTrue(result,"Method isBlank should return True  when value is null");

    }

    @Test
    public void testIsBlank_whenValueEmptyString_thenReturnTrue(){
        String value="";
        boolean result=StringUtils.isBlank(value);
        Assertions.assertTrue(result,"Method isBlank should return True  when value is empty String");

    }
    @Test
    public void testIsBlank_whenValueConsistOnlyWhiteSpacesString_thenReturnTrue(){
        String value="     ";
        boolean result=StringUtils.isBlank(value);
        Assertions.assertTrue(result,"Method isBlank should return True  when value consists only white spaces");

    }

    @Test
    public void testIsBlank_whenValueIsNotEmptyString_thenReturnTrue(){
        String value="some string value";
        boolean result=StringUtils.isBlank(value);
        Assertions.assertFalse(result,"Method isBlank should return False  when value is not empty String");

    }
}
