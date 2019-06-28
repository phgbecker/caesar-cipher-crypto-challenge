package cipher.rule;

import java.util.function.Predicate;

public class IgnorePunctuationRule implements Predicate<Character> {

    @Override
    public boolean test(Character character) {
        return String.valueOf(character).matches("[.,?!'\":;\\-—()\\[\\] ]");
    }
}
