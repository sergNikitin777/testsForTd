package ru.test.tasks;

public class StringTest {
    public static String str1 = "Съешь еще этих";
    public static String str2 = str1;
    public static StringBuilder sb1 = new StringBuilder("Съешь еще этих");
    public static StringBuilder sb2 = sb1;
    public static void main(String[] args) {
        str1 += " мягких";
        str1 += " французских";
        str2 += " булок";
        System.out.println(str1);
        System.out.println(str2);
        sb1.append(" мягких").append(" французских");
        sb2.append(" булок");
        System.out.println(sb1);
        System.out.println(sb2);
    }
}