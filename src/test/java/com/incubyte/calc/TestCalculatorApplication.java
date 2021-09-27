package com.incubyte.calc;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class TestCalculatorApplication {

    @Mock
    CalculatorApplication calculatorApplication = new CalculatorApplication();

    @Test
    @DisplayName(" Passed null and empty string to validate sum")
    public void returnZeroForEmptyOrNullString(){

        int nullStringSum = calculatorApplication.addDigits(null);
        int emptyStringSum = calculatorApplication.addDigits("");

        assertEquals(0, nullStringSum);
        assertEquals(0, emptyStringSum);
    }

    @Test
    @DisplayName(" Passed only one digit to perform sum")
    public void testWhenPassedOnlyOneDigit(){
        int sumForOneDigit1 = calculatorApplication.addDigits("1");
        int sumForOneDigit4 = calculatorApplication.addDigits("4");
        assertEquals(1, sumForOneDigit1);
        assertEquals(4, sumForOneDigit4);
    }

    @Test
    @DisplayName(" Used comma as delimiter to split numbers for 3 digits")
    public void testWhenPassedMoreThanOneDigits(){
        int sum = calculatorApplication.addDigits("1,2,3");
        assertEquals(6, sum);
    }

    @Test
    @DisplayName(" Used newline as delimiter to split numbers")
    public void testWhenDigitsAreSplitByNewLine(){
        int sum = calculatorApplication.addDigits("4\n5,6");
        assertEquals(15, sum);
    }

    @Test
    @DisplayName(" Used Random delimiter to split numbers")
    public void testWhenDigitsAreSplitByRandomDelimiter(){
        int sum = calculatorApplication.addDigits("//;\n5;6\n7");
        assertEquals(18, sum);
    }

    @Test
    @DisplayName(" One negative numbers is passed")
    public void testWhenNegativeDigitsArePassedWithRandomDelimiter(){
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                calculatorApplication.addDigits("//;\n5;6;-7"));
        assertEquals("negatives not allowed :: -7", exception.getMessage());
    }

    @Test
    @DisplayName(" Multiple negative numbers are passed")
    public void testWhenMultipleNegativeDigitsArePassedWithRandomDelimiter() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                calculatorApplication.addDigits("//;\n5;6;-7;-6"));
        assertEquals("negatives not allowed :: -7,-6", exception.getMessage());
    }

    @Test
    @DisplayName(" Passed one value greater than 1000")
    public void testWhenPassedOneValueGreaterThan1000(){
        int sum = calculatorApplication.addDigits("//;\n5;6;1007");
        assertEquals(11, sum);
    }

    @Test
    @DisplayName(" Used custom delimiter having length >=2 ")
    public void testForCustomDelimiterOfLengthGreaterOrEqualTo2(){
        int sum = calculatorApplication.addDigits("//;;\n5;;6;;1007");
        assertEquals(11, sum);
    }


    @Test
    @DisplayName(" Pass multiple delimiter of length = 1 ")
    public void testMultipleCustomDelimiterOfLengthOne(){
        int sum = calculatorApplication.addDigits("//[!][@]\n5!6@1007");
        assertEquals(11, sum);
    }

    @Test
    @DisplayName(" Pass multiple delimiter of length > 1 ")
    public void testMultipleCustomDelimiterOfLengthGreaterThanOne(){
        int sum = calculatorApplication.addDigits("//[!!][@@]\n5!!6@@1007");
        assertEquals(11, sum);
    }

    @Test
    @DisplayName(" Pass multiple delimiter of length > 1 have different group of characters for each custom delimiter")
    public void testMultipleCustomDelimiterOfLengthGreaterThanOneForDifferentChracters(){
        int sum = calculatorApplication.addDigits("//[!%][&@]\n5!%6&@1007");
        assertEquals(11, sum);
    }

    @Test
    @DisplayName(" Pass multiple delimiter of length > 1 for meta characters")
    public void testMultipleCustomDelimiterOfLengthGreaterThanOneForMetaChracters(){
        int sum = calculatorApplication.addDigits("//[**][??]\n5**6??1007");
        assertEquals(11, sum);
    }

    @Test
    @DisplayName(" Random Test")
    public void testForRandomDelimiters(){
        int sum = calculatorApplication.addDigits("//[**]\n5**6\n1007");
        assertEquals(11, sum);
    }



}
