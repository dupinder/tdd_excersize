package doc.tdd.calculator;

import doc.tdd.NegativeNumberNotAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private int count;
    public int add(String params) throws NegativeNumberNotAllowed {
        if(params.trim().equals(""))
            return 0;
        
        List<Integer> numbers = getListOfNumbers(params);

        int sum = 0;
        for (Integer num : numbers)
            sum += num;

        count++;
        return sum;
    }

    private boolean checkIfSetOfCustomDelimiter(String params) {
        return params.contains("//[") && params.contains("]\\n");
    }

    private List<Integer> StringCollectionToNumberCollection(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (String s : args)
            numbers.add(filterNumbers(s));
        return numbers;
    }

    private Integer filterNumbers(String s) {
        int number =  Integer.parseInt(s.trim().equals("") ? "0" :  s.trim());
        if(number >= 1000)
            number = 0;
       return number;
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

    private String replaceAllNewLineToDelimiter(String params, String[] delimiter) {
        return params.replaceAll("\\\\n", delimiter[0]);
    }

    private String getDelimiterFreeParams(String params, String[] delimiterSet) {
        String tmpDelimiter = params.split("\\\\n")[0].replace("//", ""); // //[*][#][^]
        String delimiterToRemove = "";
        if(delimiterSet.length > 1)
            delimiterToRemove = tmpDelimiter;
        else
            delimiterToRemove =  delimiterSet[0];
        return params.replace("//"+ delimiterToRemove +"\\n", "");
    }

    private String[] getDelimiter(String params) {
       return new String[]{params.split("\\\\n")[0].replace("//", "")};
    }

    /*
        "//[*][#]\\n1*2#3";
        1. split \\n -> [0] = //[*][#] ; [1] = 1*2#3;
        2. remove // from [0].
        3. lets say we have [#][%][^] at [0]
        4. split using '][' so we will have [0] = [# ; [1] = % ; [2] = ^]
        5. delimiter arr push [0].replace('[')
        6. delimiter arr push last element and remove ']';
    */
    private String[] getSetOfDelimiters(String params) {
        String tmpDelimiter = params.split("\\\\n")[0]; // //[*][#][^]
        tmpDelimiter = tmpDelimiter.replace("//", ""); // [*][#][^]
        String[] delimiterSet = tmpDelimiter.split("]\\[");
        delimiterSet[0] = delimiterSet[0].replace("[", "");
        delimiterSet[delimiterSet.length-1] = delimiterSet[delimiterSet.length-1].replace("]", "");
        return delimiterSet;
    }

    private boolean checkIfCustomDelimiter(String params) {
        //if String starts with // it means String have it's own delimiter.
        return params.startsWith("//");
    }

    public int getCalledCount() {
        return this.count;
    }



    private List<Integer> getListOfNumbers(String params) throws NegativeNumberNotAllowed {
        String[] delimiterSet =  getDelimiterFromParams(params);
        boolean customSetOfDelimiters = delimiterSet.length > 1;
        List<Integer> numbers = new ArrayList<>();
        params = getDelimiterFreeParams(params, delimiterSet);

        if(customSetOfDelimiters)
        {
            for (String s: delimiterSet) {
                String strRegex = Pattern.quote(s);
                String strReplacement = "";
                Pattern p = Pattern.compile(strRegex);
                Matcher m = p.matcher(params);
                params = m.replaceAll(strReplacement);
            }

            for (String s : params.split("")) {
                numbers.add(Integer.parseInt(s));
            }
        }
        else
        {
            params = replaceAllNewLineToDelimiter(params, delimiterSet);
            numbers = StringCollectionToNumberCollection(params.split(delimiterSet[0]));
        }

        manageNegativeNumber(numbers);
        return numbers;
    }


    private String[] getDelimiterFromParams(String params) {

        String[] delimiterSet = new String[]{","};
        if(checkIfCustomDelimiter(params))
            if(checkIfSetOfCustomDelimiter(params))
                delimiterSet = getSetOfDelimiters(params);

            else
                delimiterSet = getDelimiter(params);

        return delimiterSet;
    }

    }
