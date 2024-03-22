package com.example.fetchapp;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortByName implements Comparator<itemModel>{
    /*
    This class sorts each list by name by using regex patterns to get the numbers
    off of the item name. It then compares the numbers with the extracted number of 1 itemModel with
    another number from a second itemModel and does so until the list is sorted
     */

    //Regex pattern
    private static final Pattern pattern = Pattern.compile("Item (\\d+)");

    //Compare two numbers at a time
    @Override
    public int compare(itemModel item1, itemModel item2) {
        int number1 = extractNumber(item1.getName());
        int number2 = extractNumber(item2.getName());
        return Integer.compare(number1, number2);
    }

    //Method to extract number off of item name
    private int extractNumber(String name) {
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        //If no number found, return a large value to ensure it's placed at the end
        return Integer.MAX_VALUE;
    }
}
