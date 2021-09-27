package com.incubyte.calc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class CalculatorApplication {

    public static final String DELIMITER_SEPARATOR = "//";
    public static final String NEWLINE_SEPARATOR = "\n";
    public static final String DEFAULT_DELIMITER = ",";
    public static final int MAX_VALUE = 1000;
    public static final List<String> META_CHAR_LIST =  Arrays.asList("+","*","$","?","^");
    public int addDigits(String numbers){
        int sum = 0;
        if(numbers!=null && !numbers.isEmpty()){
            String[] digitsArray = splitSeriesOfDigits(numbers);
            sum = sumOfDigits(sum, digitsArray);
        }
        return sum;
    }

    private String[] splitSeriesOfDigits(String numbers) {
        String seriesOfDigits = numbers;
        String delimiter = "";

        if(seriesOfDigits.startsWith(DELIMITER_SEPARATOR)){
            String[] customDelimiters = seriesOfDigits.split(NEWLINE_SEPARATOR,2);
            delimiter = getCustomDelimiter(customDelimiters[0]);
            seriesOfDigits = customDelimiters[1];
        }
        delimiter = addDefaultDelimiter(delimiter);
        String[] digitsArray = seriesOfDigits.split(delimiter);
        return digitsArray;
    }

    private String addDefaultDelimiter(String delimiter) {
        return delimiter.isEmpty() ? getDefaultDelimiter() : (delimiter + "|" + getDefaultDelimiter());
    }

    private String getDefaultDelimiter() {
        return NEWLINE_SEPARATOR + "|" + DEFAULT_DELIMITER;
    }

    private String getCustomDelimiter(String customDelimiterList) {
        String innerDelimiters = customDelimiterList.replaceFirst(DELIMITER_SEPARATOR,"");
        String customDelimiters = getAllCustomDelimiters(innerDelimiters);
        return customDelimiters;
    }

    private String getAllCustomDelimiters(String innerDelimiters) {
        Predicate<String> checkDelimiterIsNotEmpty = innerDelimiter -> innerDelimiter!=null && !innerDelimiter.isEmpty();

        String customDelimiters = Arrays.stream(innerDelimiters.split("\\["))
                .filter(checkDelimiterIsNotEmpty)
                .map(this::replaceMetaCharacters)
                .collect(Collectors.joining("|"));

        return customDelimiters;
    }

    private String replaceMetaCharacters(String delimiterValue) {
        BiFunction<String, String, String> replaceMetaChar = (delimiter, metaChar) -> delimiter.contains(metaChar) ?
                delimiter.replaceAll("\\"+metaChar, "\\\\"+metaChar) : delimiter;
        String delimiter = delimiterValue.replaceAll("]","");
        for(String metaChar : META_CHAR_LIST){
            delimiter = replaceMetaChar.apply(delimiter, metaChar);
        }
        return delimiter;
    }

    private int sumOfDigits(int sum, String[] digitsArray) {
        List<String> negativeNumbers = new ArrayList<>();
        Predicate<String> numberIsNotEmpty = number -> number!=null && !number.isEmpty();
        Predicate<String> isNegativeNumber = number -> parseInt(number) < 0;
        Function<String, Integer> getIntValue = this::parseInt;
        Function<Integer, Integer> isGreaterThan1000 = number -> number >= MAX_VALUE ?  0 : number;

        for(String  digit : digitsArray){
            if(numberIsNotEmpty.test(digit)) {
                if(isNegativeNumber.test(digit)) {
                    negativeNumbers.add(digit);
                }else {
                    sum += getIntValue.andThen(isGreaterThan1000).apply(digit);
                }
            }
        }

        if(negativeNumbers.size() > 0){
            throw new IllegalArgumentException("negatives not allowed :: "+String.join(",", negativeNumbers));
        }
        return sum;
    }

    private int parseInt(String number) {
        return Integer.parseInt(number);
    }
}
