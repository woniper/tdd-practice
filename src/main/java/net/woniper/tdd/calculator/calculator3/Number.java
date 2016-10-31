package net.woniper.tdd.calculator.calculator3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by woniper on 2016. 10. 5..
 */
public class Number {

    private List<Integer> numbers = new ArrayList<>();

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void addNumber(int number) {
        numbers.add(number);
    }

    public int sum() {
        return getNumbers().stream().mapToInt(Integer::intValue).sum();
    }

    public int multiply() {
        return getNumbers().stream().mapToInt(Integer::intValue).reduce((left, right) -> left * right).getAsInt();
    }
}
