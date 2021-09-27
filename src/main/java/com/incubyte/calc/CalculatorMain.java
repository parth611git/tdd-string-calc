package com.incubyte.calc;

public class CalculatorMain {
    public static void main(String[] args) {
        CalculatorApplication calcApp = new CalculatorApplication();
        int sum = calcApp.addDigits("//;\n5;6;1000");
        System.out.println(sum);
    }
}
