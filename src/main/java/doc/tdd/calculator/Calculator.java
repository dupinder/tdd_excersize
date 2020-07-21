package doc.tdd.calculator;

import doc.tdd.NegativeNumberNotAllowed;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private int count;
    public int add(String params) throws NegativeNumberNotAllowed {
        count++;
        String delimiter = ",";
        if(params.trim().equals(""))
            return 0;

        if(checkIfCustomDelimiter(params)){
            delimiter = getDelimiter(params);
            params = getDelimiterFreeParams(params, delimiter);
        }
        
        params = replaceAllNewLineToDelimiter(params, delimiter);
        String[] args = params.split(delimiter);

        List<Integer> numbers = StringArrayToNumberArray(args);

        manageNegativeNumber(numbers);

        int sum = 0;
        for (Integer num : numbers){
            sum += num;
        }

        return sum;
    }

    private List<Integer> StringArrayToNumberArray(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (String s : args)
            numbers.add(Integer.parseInt(s.trim().equals("") ? "0" : s.trim()));

        return numbers;
    }

    private void manageNegativeNumber(List<Integer> number) throws NegativeNumberNotAllowed {
        List<Integer> negativeNumbers = new ArrayList<>();
        for (Integer num : number){
            if( num < 0)
                negativeNumbers.add(num);
        }
        if(negativeNumbers.size() > 0)
            new NegativeNumberNotAllowed().negativeNumberNotAllowedException(negativeNumbers, "negatives not allowed");

    }

    private String replaceAllNewLineToDelimiter(String params, String delimiter) {
        // new line will act as next number for sum
        return params.replaceAll("\\\\n", delimiter);
    }

    private String getDelimiterFreeParams(String params, String delimiter) {
        return params.replace("//"+delimiter+"\\n", "");
    }

    private String getDelimiter(String params) {
       return params.split("\\\\n")[0].replace("//", "");
    }

    private boolean checkIfCustomDelimiter(String params) {
        //if String starts with // it means String have it's own delimiter.
        return params.startsWith("//");
    }

    public int getCalledCount() {
        return this.count;
    }
}
