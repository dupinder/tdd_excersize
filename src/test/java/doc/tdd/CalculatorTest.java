package doc.tdd;

import doc.tdd.calculator.Calculator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CalculatorTest {
    public static Calculator cal = null;
    @BeforeAll
    public static void init(){
     cal = new Calculator();
    }


    @Test
    public void sum() throws NegativeNumberNotAllowed {

        int sum = cal.add("2,3");
        assertEquals(sum, 5);

        int sumWithSpace = cal.add(" 2, 3");
        assertEquals(sumWithSpace, 5);

        // if one number is empty it means number is zero
        int sumWithFirstZero = cal.add(",3");
        assertEquals(sumWithFirstZero, 3);

        int sumWithSecondZero = cal.add("2, ");
        assertEquals(sumWithSecondZero, 2);

        // for an empty string it will return 0.
        int sumWithEmptyString = cal.add("");
        assertEquals(sumWithEmptyString, 0);


        int sumAbnormalParams = cal.add("1, 2, 3");
        assertEquals(sumAbnormalParams, 6);

        int sumWithNewLine = cal.add("1 \\n 2, 3");
        assertEquals(sumWithNewLine, 6);

        int sumCustomDelimiter = cal.add("//;\\n1;2");
        assertEquals(sumCustomDelimiter, 3);

        int sumCustomDelimiter2 = cal.add("//-\\n1-2");
        assertEquals(sumCustomDelimiter2, 3);

        int sumCustomDelimiter3 = cal.add("//;\\n1;2 \\n 3");
        assertEquals(sumCustomDelimiter3, 6);
    }

    @Test
    public void sumWithOneNegativeNumber(){
        try{
            int sumWithNegativeNumber = cal.add("1, -2");
        }
        catch (NegativeNumberNotAllowed negativeNumberNotAllowed){
            assertTrue(negativeNumberNotAllowed
                    .getCustomMessage()
                    .equals("negatives not allowed "+"-2"));
        }

    }

    @Test
    public void sumWithMultipleNegativeNumbers(){

        try{
            int sumWithNegativeNumber = cal.add("-1, -2");
        }
        catch (NegativeNumberNotAllowed negativeNumberNotAllowed){
            assertTrue(negativeNumberNotAllowed
                    .getCustomMessage()
                    .equals("negatives not allowed "+"-1 -2"));
        }
    }


    @Test
    @AfterAll
    public static void testGetCalledCount(){
       int count = cal.getCalledCount();
       assertEquals(12, count);
    }

}
