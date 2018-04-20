package ru.test.tasks;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testInit(){
        Init.Derived d = new Init.Derived();
        d.print();
    }

    @Test
    public void testStringTest(){
        StringTest.str1 += " мягких";
        StringTest.str1 += " французских";
        StringTest.str2 += " булок";
        System.out.println(StringTest.str1);
        System.out.println(StringTest.str2);
        StringTest.sb1.append(" мягких").append(" французских");
        StringTest.sb2.append(" булок");
        System.out.println(StringTest.sb1);
        System.out.println(StringTest.sb2);
    }

    @Test
    public void testTypeConv(){
        System.out.println((int)(char)(byte)-1);
    }

    @Test
    public void find() {

        int[] ints = {1,2,3,4,5,6,7,8,9,10};

        Arrays.sort(ints);

        int result =  App.find(ints,8);

        Assert.assertEquals(7,result);
    }

    @Test
    public void isLeapYear() throws Exception {

       Assert.assertTrue( App.isLeapYear("22-12-2004"));
       Assert.assertFalse( App.isLeapYear("22-12-2005"));
       Assert.assertTrue( App.isLeapYear("22-12-2008"));

       try{
           App.isLeapYear("22-12-1599");
       }catch (Exception e){
           Assert.assertTrue(e.getMessage().equals("год вне допустимого диапазона"));
       }

        try{
            App.isLeapYear("22-12-10000");
        }catch (Exception e){
            Assert.assertTrue(e.getMessage().equals("год вне допустимого диапазона"));
        }
    }
}
