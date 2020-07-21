package doc.tdd;

import java.util.List;
import java.util.StringJoiner;

public class NegativeNumberNotAllowed extends Throwable {

    private List<Integer> numbers;
    private String message;

    public NegativeNumberNotAllowed(List<Integer> numbers, String message) {
        this.numbers = numbers;
        this.message = message;
    }

    public NegativeNumberNotAllowed(){

    }

    public void negativeNumberNotAllowedException(List<Integer> numbers, String message) throws NegativeNumberNotAllowed {
        throw new NegativeNumberNotAllowed(numbers, message);
    }

    public String getCustomMessage(){
        return this.message+" "+getMeString(this.numbers);
    }

    public String getMeString(List<Integer> numbers) {
        StringJoiner sj = new StringJoiner(" ");
        numbers.forEach( n -> sj.add(n.toString()));
        return sj.toString();
    }
}
