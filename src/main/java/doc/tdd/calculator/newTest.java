package doc.tdd.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class newTest{
    public static void main(String args[]){

        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);numbers.add(2);

        System.out.println(getMeString(numbers));
    }

    private static String getMeString(List<Integer> numbers) {
        StringJoiner sj = new StringJoiner(" ");
        numbers.forEach( n -> sj.add(n.toString()));
        return sj.toString();
    }
}
